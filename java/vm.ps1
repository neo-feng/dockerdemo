#设置初始变量

$cred = Get-Credential

$rgName = "dockersession"

$location = "southeastasia"

$imageName = "dockercentos-image-1"

$storageType = 'Standard_LRS'

$myVnet = "dockersession-vnet"

$nsgName = "imagevm-nsg"

$vmSize = "Standard_B1s"

#读取镜像文件、虚拟网络、网络安全组信息

$image = Get-AzureRMImage -ImageName $imageName -ResourceGroupName $rgName

$vnet = Get-AzureRmVirtualNetwork -Name $myVnet -ResourceGroupName $rgName

$nsg = Get-AzureRmNetworkSecurityGroup -ResourceGroupName $rgName -Name $nsgName

#使用循环创建VM，默认数量为2（可调整）

for($i=2;$i –le 21;$i++)

{

#设置虚拟机名称变量

$vmName = "dockerVM" +$i;

$nicName = "$vmName-Nic" ;

$dataDiskName = "$vmName-DataDisk01" ;

$ipName = "$vmName-Pip";

#建立虚拟网卡

$pip = New-AzureRmPublicIpAddress -Name $ipName -ResourceGroupName $rgName -Location $location -AllocationMethod Dynamic ;

$nic = New-AzureRmNetworkInterface -Name $nicName -ResourceGroupName $rgName -Location $location -SubnetId $vnet.Subnets[0].Id -PublicIpAddressId $pip.Id -NetworkSecurityGroupId $nsg.Id ;

#设置并建立500GB的附加数据磁盘

$diskConfig = New-AzureRmDiskConfig -SkuName $storageType -Location $location -CreateOption Empty -DiskSizeGB 500 ;

$dataDisk1 = New-AzureRmDisk -DiskName $dataDiskName -Disk $diskConfig -ResourceGroupName $rgName ;

#建立VM配置文件

$vm = New-AzureRmVMConfig -VMName $vmName -VMSize $vmSize;

$vm = Set-AzureRmVMSourceImage -VM $vm -Id $image.Id ;

$vm = Set-AzureRmVMOSDisk -VM $vm -StorageAccountType $storageType -DiskSizeInGB 128 -CreateOption FromImage -Caching ReadWrite ;

$vm = Set-AzureRmVMOperatingSystem -VM $vm -Windows -ComputerName $vmName -Credential $cred -ProvisionVMAgent -EnableAutoUpdate ;

$vm = Add-AzureRmVMNetworkInterface -VM $vm -Id $nic.Id ;

$vm = Add-AzureRmVMDataDisk -VM $vm -Name $dataDiskName -CreateOption Attach -ManagedDiskId $dataDisk1.Id -Lun 1 ;

#建立VM

New-AzureRmVM -VM $vm -ResourceGroupName $rgName -Location $location -AsJob;

}