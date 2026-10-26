
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/Gembali-tejasri7/pharmacy-inventory'
            }
        }

        stage('Build') {
            steps {
                bat 'mvnw.cmd clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvnw.cmd test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t pharmacy-inventory:1.0 .'
            }
        }
    }
}
