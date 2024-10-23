## Note EC2 CMD
- Install jdk:
    + sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
    + sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io.key
    + sudo amazon-linux-extras install epel
    + sudo amazon-linux-extras install java-openjdk11
    + sudo yum install --nogpgcheck jenkins
    + sudo systemctl start jenkins
    + sudo more /var/lib/jenkins/secrets/initialAdminPassword
    + Install git and github plugin
    + Gen personal access key in github and connect with jenkin
    + Install git and maven in ec2