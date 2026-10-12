// Jenkins version of .github/workflows/test.yml.
// Needs "JDK17" and "Maven3" set up under Manage Jenkins > Tools, and the JUnit plugin.
pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven3'
    }

    parameters {
        string(name: 'TAGS', defaultValue: '@smoke or @regression',
               description: 'Cucumber tags to run, e.g. @smoke or @checkout')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Unit tests') {
            steps {
                sh "mvn -B test -Dtest='PriceUtilsTest,SortUtilsTest,TestUserRepositoryTest'"
            }
        }

        stage('UI tests') {
            steps {
                sh "mvn -B test -Dtest=RunCucumberTest -Dheadless=true -Dcucumber.filter.tags='${params.TAGS}'"
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/cucumber-reports/**', allowEmptyArchive: true
        }
    }
}
