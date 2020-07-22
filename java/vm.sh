#!/bin/bash

REGION="southeastasia" #替换成中东的区域名称
RGNAME="dockersession"
VMNAME="dockervm-" #虚机名字前缀
VMSIZE="Standard_B1s" #替换成你的型号 Standard_D64s_v3
IMAGE="dockercentos-image-1"   # /subscriptions/1bab3e78-2764-4555-845d-297f7a7ca7c0/resourceGroups/Tencent-india-s-game/providers/Microsoft.Compute/galleries/centos74/images/centos74/versions/0.0.1" #OpenLogic:CentOS:7.4:7.4.20171206"   #建议不要改
VNET="dockersession-vnet"  #替换成你的虚拟网络名字
SUBNET="default"    #替换成你的子网名字
USERNAME="lnadmin"   #替换成你的用户名，不能为root
PASSWORD="Lnadmin_123456" #替换成你的密码
#DATADISKSIZE=200
#OSDISKSIZE=50
STORAGETYPE="Standard_SSD" #Premium_LRS"
#AVAILABILITYSET="avset-bat1-"$REGION
NSG="dockervm1427"

iSTART=2          #起始序号
VMCOUNT=2      #结束序号

# az account set -s 1bab3e78-2764-4555-845d-297f7a7ca7c0
starttime=`date +'%Y-%m-%d %H:%M:%S'`
#echo 创建资源组
#az group create -n $RGNAME -l $REGION

#echo 创建VNet
#az network vnet create --resource-group $RGNAME --location $REGION --name $VNET --subnet-name $SUBNET


#echo 创建可用性集
#az vm availability-set create --resource-group $RGNAME --name $AVAILABILITYSET --platform-fault-domain-count 2 --platform-update-domain-count 3

#echo 创建网络安全组NSG
#az network nsg create --resource-group $RGNAME --name $NSG

# echo Create a network security group rule for port 22.
# az network nsg rule create --resource-group $RGNAME --nsg-name $NSG --name myNetworkSecurityGroupRuleSSH \
#   --protocol tcp --direction inbound --source-address-prefix '*' --source-port-range '*'  \
#   --destination-address-prefix '*' --destination-port-range 22 --access allow --priority 1000

echo 创建n个公网IP和VM网卡
for i in `seq $iSTART $VMCOUNT`; do
{
  # Create a public IP address.
  echo $VMNAME$i"-ip"
  az network public-ip create --sku Standard --resource-group $RGNAME --name $VMNAME$i"-pip" --allocation-method static
  echo $VMNAME$i"-nic"
  az network nic create \
    --resource-group $RGNAME --name $VMNAME$i"-nic" \
    --vnet-name $VNET --subnet $SUBNET \
    --network-security-group $NSG --public-ip-address $VMNAME$i"-pip"

#--accelerated-networking true \  #需要加速网络可以加上

  #修改内网IP为静态
  az network nic update -g $RGNAME -n $VMNAME$i"-nic" \
    --set ipConfigurations[name=ipconfig1].privateIpAllocationMethod="static" --no-wait
} &

done

wait #等待并发建网卡批次完成

echo 创建n个VM
for i in `seq $iSTART $VMCOUNT`; do
{
  echo $VMNAME$i
  az vm create \
    --name $VMNAME$i --resource-group $RGNAME --image $IMAGE \
    --admin-username $USERNAME --admin-password $PASSWORD \
    --size $VMSIZE --nics $VMNAME$i"-nic" \
 #   --availability-set $AVAILABILITYSET \
    --storage-sku $STORAGETYPE


  # 执行自定义初始化脚本
#   echo $VMNAME$i"-dsc"
#   az vm extension set \
#       --publisher Microsoft.Azure.Extensions \
#       --name CustomScript \
#       --version 2.0 \
#       --settings '{"fileUris": ["https://rade.blob.core.windows.net/dsc/pubgdsc.sh"], "commandToExecute":"./pubgdsc.sh '$PASSWORD' /data "}'\
#       --vm-name $VMNAME$i \
#       --resource-group $RGNAME

  # az vm restart -g $RGNAME -n $VMNAME$i --no-wait
} &
done
wait   #等待并发批次完成

echo "All done"
endtime=`date +'%Y-%m-%d %H:%M:%S'`
start_seconds=$(date --date="$starttime" +%s);
end_seconds=$(date --date="$endtime" +%s);
echo "本次运行时间： "$((end_seconds-start_seconds))"s"