terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "4.40.0"
    }
    backend "s3" {
      bucket = "analytics-${var.candidate_id}"
      key = "<key>/apprunner-lab.state"
      region = "eu-north-1"
    }
  }
}