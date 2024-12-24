provider "aws" {
  access_key = "test"
  secret_key = "test"
  region     = "us-east-1"

  skip_credentials_validation = true
  skip_metadata_api_check     = true
  skip_requesting_account_id  = true

  endpoints {
    apigateway = "http://localhost:4566"
  }
}

// API GATEWAY
resource "aws_api_gateway_rest_api" "api" {
  name        = "challenge-api-gateway"
  description = "API gateway to local challenge REST API service"
}

// RESOURCES
resource "aws_api_gateway_resource" "cadastros" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_rest_api.api.root_resource_id
  path_part   = "cadastros"
  depends_on = [
    aws_api_gateway_rest_api.api
  ]
}

resource "aws_api_gateway_resource" "adicionar" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.cadastros.id
  path_part   = "adicionar"
  depends_on = [
    aws_api_gateway_rest_api.api
  ]
}

resource "aws_api_gateway_resource" "cadastro_id" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  parent_id   = aws_api_gateway_resource.cadastros.id
  path_part   = "{cadastroId}"
  depends_on = [
    aws_api_gateway_rest_api.api
  ]
}

// METHODS
resource "aws_api_gateway_method" "post_cadastro" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.adicionar.id
  authorization = "NONE"
  http_method   = "POST"
  depends_on = [
    aws_api_gateway_resource.adicionar
  ]
}

resource "aws_api_gateway_method" "get_cadastro" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.cadastro_id.id
  authorization = "NONE"
  http_method   = "GET"
  request_parameters = {
    "method.request.path.cadastroId" = true
  }
  depends_on = [
    aws_api_gateway_resource.cadastro_id
  ]
}

resource "aws_api_gateway_method" "get_cadastros" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.cadastros.id
  authorization = "NONE"
  http_method   = "GET"
  depends_on = [
    aws_api_gateway_resource.cadastros
  ]
}

resource "aws_api_gateway_method" "patch_cadastro" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.cadastro_id.id
  authorization = "NONE"
  http_method   = "PATCH"
  request_parameters = {
    "method.request.path.cadastroId" = true
  }
  depends_on = [
    aws_api_gateway_resource.cadastro_id
  ]
}

resource "aws_api_gateway_method" "delete_cadastro" {
  rest_api_id   = aws_api_gateway_rest_api.api.id
  resource_id   = aws_api_gateway_resource.cadastro_id.id
  authorization = "NONE"
  http_method   = "DELETE"
  request_parameters = {
    "method.request.path.cadastroId" = true
  }
  depends_on = [
    aws_api_gateway_resource.cadastro_id
  ]
}

// INTEGRATIONS
resource "aws_api_gateway_integration" "post_cadastro" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.adicionar.id
  http_method             = aws_api_gateway_method.post_cadastro.http_method
  type                    = "HTTP_PROXY"
  uri                     = "https://host.docker.internal:8443/cadastros/adicionar"
  integration_http_method = "POST"
  depends_on = [
    aws_api_gateway_method.post_cadastro
  ]
}

resource "aws_api_gateway_integration" "get_cadastro" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.cadastro_id.id
  http_method             = aws_api_gateway_method.get_cadastro.http_method
  type                    = "HTTP_PROXY"
  uri                     = "https://host.docker.internal:8443/cadastros/{cadastroId}"
  integration_http_method = "GET"
  request_parameters = {
    "integration.request.path.cadastroId" = "method.request.path.cadastroId"
  }
  depends_on = [
    aws_api_gateway_method.get_cadastro
  ]
}

resource "aws_api_gateway_integration" "get_cadastros" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.cadastros.id
  http_method             = aws_api_gateway_method.get_cadastros.http_method
  type                    = "HTTP_PROXY"
  uri                     = "https://host.docker.internal:8443/cadastros"
  integration_http_method = "GET"
  depends_on = [
    aws_api_gateway_method.get_cadastros
  ]
}

resource "aws_api_gateway_integration" "patch_cadastro" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.cadastro_id.id
  http_method             = aws_api_gateway_method.patch_cadastro.http_method
  type                    = "HTTP_PROXY"
  uri                     = "https://host.docker.internal:8443/cadastros/{cadastroId}"
  integration_http_method = "PATCH"
  request_parameters = {
    "integration.request.path.cadastroId" = "method.request.path.cadastroId"
  }
  depends_on = [
    aws_api_gateway_method.patch_cadastro
  ]
}

resource "aws_api_gateway_integration" "delete_cadastro" {
  rest_api_id             = aws_api_gateway_rest_api.api.id
  resource_id             = aws_api_gateway_resource.cadastro_id.id
  http_method             = aws_api_gateway_method.delete_cadastro.http_method
  type                    = "HTTP_PROXY"
  uri                     = "https://host.docker.internal:8443/cadastros/{cadastroId}"
  integration_http_method = "DELETE"
  request_parameters = {
    "integration.request.path.cadastroId" = "method.request.path.cadastroId"
  }
  depends_on = [
    aws_api_gateway_method.delete_cadastro
  ]
}

// DEPLOYMENT
resource "aws_api_gateway_deployment" "deployment" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  depends_on = [
    aws_api_gateway_integration.post_cadastro,
    aws_api_gateway_integration.get_cadastro,
    aws_api_gateway_integration.get_cadastros,
    aws_api_gateway_integration.patch_cadastro,
    aws_api_gateway_integration.delete_cadastro
  ]
}

// STAGE
resource "aws_api_gateway_stage" "stage" {
  rest_api_id = aws_api_gateway_rest_api.api.id
  deployment_id = aws_api_gateway_deployment.deployment.id
  stage_name = "dev"
  depends_on = [
    aws_api_gateway_deployment.deployment
  ]
}