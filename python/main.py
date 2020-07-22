from flask import Flask
from waitress import serve
from flasgger import Swagger
from flasgger.utils import swag_from
from flasgger import LazyString, LazyJSONEncoder
from flask_cors import CORS, cross_origin
from flask import request
import json

app = Flask(__name__)
app.config["SWAGGER"] = {"title": "Create your first docker", "uiversion": 2}
app.config['CORS_HEADERS'] = 'Content-Type'
cors = CORS(app, resources={r"/": {"origins": "*"}})
swagger_config = {
    "headers": [],
    "specs": [
        {
            "endpoint": "docker_test",
            "route": "/docker_test.json",
            "rule_filter": lambda rule: True,  # all in
            "model_filter": lambda tag: True,  # all in
        }
    ],
    "static_url_path": "/flasgger_static",
    #"static_folder": "/static",  # must be set by user
    "swagger_ui": True,
    "specs_route": "/api/",
}

template = dict(
    swaggerUiPrefix=LazyString(lambda: request.environ.get("HTTP_X_SCRIPT_NAME", ""))
)

app.json_encoder = LazyJSONEncoder
swagger = Swagger(app, config=swagger_config, template=template)

@app.route("/api/dockertest", methods=["POST"]) # this route is the default one
@swag_from("swagger_config_test.yml")
def home():
    input_json = request.get_json()
    name = str(input_json["name"])
    print("Hello " + name + ", we have Flask in a Docker container!!")
    return json.dumps({"result": "Hello " + name + ", we have Flask in a Docker container!!"})

if  __name__  ==  "__main__":
    serve(app, host='0.0.0.0', port=5000)
    #app.run(debug=True, host='0.0.0.0')