@Library('com.org.jenkins.pipeline.library@v0.2.1') _
import com.org.jenkins.pipeline.library.versions.SemVer

pipeline {
  agent {
    label 'docker-maven-slave'
  }
  environment {
    JAVA_VERSION = '1.8'

    GIT_SONAR_TOKEN = 'sonar-github'

    DOCKER_IMAGE = 'docker.com/OWNER/KEBAB_NAME'

    K8S_CLUSTER = 'k8s-prod-ctc-aci.org.com'
    K8S_NAMESPACE = 'reuse'
  }

  stages {
    stage ('Version') {
      when { branch 'master' }
      steps {
        script {
          gitInfo = new Git(this)
          env.GITHUB_REPO = "${gitInfo.getOrg()}/${gitInfo.getGitRemoteRepoName()}"

          env.LAST_RELEASE_VERSION = glVersionsGetLatestSemanticVersionFromTag gitTagPrefix: "${env.GIT_TAG_PREFIX}"
          env.RELEASE_VERSION = determineReleaseVersion(env.LAST_RELEASE_VERSION)
        }
      }
    }
    stage('Build') {
      steps {
        glMavenBuild javaVersion: env.JAVA_VERSION,
            surefireReportsPath: '**/target/failsafe-reports/*.xml, **/target/surefire-reports/*.xml',
            additionalProps: ['ci.env': '']
      }
    }
    stage('Sonar') {
      steps {
        glSonarMavenScan gitUserCredentialsId: env.GIT_SONAR_TOKEN
      }
    }
  
    stage('Build Docker Image') {
      steps {
        script {
          String dockerCredential='docker-creds'
          String dockerTag="${env.RELEASE_VERSION}"
          glDockerImageBuildPush tag: "${env.DOCKER_IMAGE}:development", dockerCredentialsId: $dockerCredential
        }
      }
    }
    stage('Deploy') {
      when { branch 'master' }
      steps {
        script {
          helmParams = [credentialsId           : 'kubernetes-credential-id',
                        cluster                 : "${env.K8S_CLUSTER}",
                        namespace               : "${env.K8S_NAMESPACE}",
                        timeout                 : 600,
                        deploymentName          : 'KEBAB_NAME',
                        chartFolder             : 'helm/KEBAB_NAME',
                        maskInlineValuesFromLogs: true,
                        inlineValues            : ["image.tag=${env.RELEASE_VERSION}"],
                        valueFiles              : ['helm/KEBAB_NAME/values.yaml', 'helm/KEBAB_NAME/env/prod.yaml']
          ]

          glKubernetesHelmChart helmParams
        }
      }
    }
  }
  post {
    always {
      echo 'This will always run'
      emailext body: "Build URL: ${BUILD_URL}",
          subject: "$currentBuild.currentResult-$JOB_NAME",
          to: "$jenkinsBuildEmailTo"
    }
    success {
      echo 'This will run only if successful'
    }
    failure {
      echo 'This will run only if failed'
    }
    unstable {
      echo 'This will run only if the run was marked as unstable'
    }
    changed {
      echo 'This will run only if the state of the Pipeline has changed'
      echo 'For example, if the Pipeline was previously failing but is now successful'
    }
  }
}

def determineReleaseVersion(lastReleaseVersion) {
  def packageVersion = sh script: 'mvn help:evaluate -Dexpression=project.version -q -DforceStdout', returnStdout: true


  return new SemVer(packageVersion) > new SemVer(lastReleaseVersion)
      ? packageVersion
      : glVersionsBump(version: lastReleaseVersion)
}
