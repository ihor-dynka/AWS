!#/bin/bash
sudo yum install -y httpd
sudo systemctl start httpd.service
sudo systemctl enable httpd.service
sudo service httpd status
sudo mkdir my_simple_app
aws s3 cp s3://task-3-bucket-ihor-dynka/simple_app/ my_simple_app --recursive
sudo cp -R my_simple_app /var/www/html