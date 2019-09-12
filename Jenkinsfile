// While you can't use Groovy's .collect or similar methods currently, you can
// still transform a list into a set of actual build steps to be executed in
// parallel.

// Our initial list of strings we want to echo in parallel
def stringsToEcho = ["a", "b", "c", "d"]

// The map we'll store the parallel steps in before executing them.
def stepsForParallel = stringsToEcho.collectEntries {
    ["echoing ${it}" : transformIntoStep(it)]
}


// Take the string and echo it.
def transformIntoStep(inputString) {
    // We need to wrap what we return in a Groovy closure, or else it's invoked
    // when this method is called, not when we pass it to parallel.
    // To do this, you need to wrap the code below in { }, and either return
    // that explicitly, or use { -> } syntax.
    return {
        node {
            echo inputString
        }
    }
}

node {
    stage('Environment') {
        parallel {
            stage('jenkins') {
                sh 'printenv'
            }
            stage('java') {
                sh 'which java'
                sh 'java -version'
            }
            stage('maven') {
                sh 'which mvn'
                sh 'mvn -v'
            }
        }
    }
    stage('Build') {
        echo 'Building....'
    }
    stage('Test') {
        echo 'Testing....'
    }
    stage('Delivery') {
        parallel stepsForParallel
    }
    stage('Deploy') {
        echo 'Deploying....'
    }
}