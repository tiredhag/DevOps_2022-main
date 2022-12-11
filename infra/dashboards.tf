resource "aws_cloudwatch_dashboard" "main" {
  dashboard_name = var.candidate_id
## Jim; seriously! we can use any word here.. How cool is that?
  dashboard_body = <<DASHBOARD
{
  "widgets": [
    {
      "type": "metric",
      "x": 0,
      "y": 0,
      "width": 12,
      "height": 6,
      "properties": {
        "metrics": [
          [
            "${var.candidate_id}",
            "carts.value",
            "checkouts.value",
            "total_carts.value",
            "checkout_latency.avg"
          ]
        ],
        "period": 300,
        "stat": "Maximum",
        "region": "eu-west-1",
        "title": "Carts in System"
      }
    }
  ]
}
DASHBOARD
}