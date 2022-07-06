#!/bin/bash
sudo mkdir my_simple_app
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.34.0/install.sh | bash
. ~/.nvm/nvm.sh
nvm install --lts
cd my_simple_app
export HOST=http://internal-task4autoscallinggroup1-2130842876.us-east-1.elb.amazonaws.com/
export PORT=300
npm i
npm start