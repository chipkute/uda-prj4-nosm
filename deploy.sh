#!/bin/bash

SSH_KEY="/home/ec2-user/nosm-ec2-key.pem"
EC2_USER="ec2-user"
EC2_IP="ec2-18-234-94-74.compute-1.amazonaws.com"

REMOTE_DIR="/home/ec2-user"       # Thư mục trên EC2
WAR_FILE="auth-course-0.0.1-SNAPSHOT.war"  # Tên file WAR
LOCAL_WAR_PATH="target/$WAR_FILE" # Đường dẫn đến file WAR trong thư mục target
REMOTE_WAR="$REMOTE_DIR/$WAR_FILE" # Đường dẫn đến file WAR trên EC2

# Bước 1: Dừng ứng dụng nếu đang chạy
echo "Stopping existing application..."
ssh -i $SSH_KEY $EC2_USER@$EC2_IP "pkill -f 'java -jar $REMOTE_WAR'"

# Bước 2: Copy file WAR từ Jenkins đến EC2
echo "Copying WAR file to EC2..."
scp -i $SSH_KEY $LOCAL_WAR_PATH $EC2_USER@$EC2_IP:$REMOTE_DIR/

# Bước 3: Chạy ứng dụng Spring Boot mới
echo "Starting the application..."
ssh -i $SSH_KEY $EC2_USER@$EC2_IP "nohup java -jar $REMOTE_WAR > /dev/null 2>&1 &"

echo "Deployment completed successfully!"

# Bước 2: SSH vào EC2, dừng ứng dụng cũ và khởi động lại ứng dụng
echo "Deploying application on EC2..."
ssh -i $SSH_KEY $EC2_USER@$EC2_IP << EOF
    echo "Stopping existing application (if any)..."
    pkill -f 'java -jar' || true
    echo "Starting new application..."
    nohup java -jar $REMOTE_JAR > app.log 2>&1 &
    echo "Application deployed!"
EOF
