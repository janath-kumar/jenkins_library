def call(String[] params) {
		appl = params[0]
		tomcatl = params[1]
		sh "find -name '*.java' > sources_file.txt"
		echo "Finished collecting source files"
		sh "javac  -cp .:./${appl}/*:./${tomcatl}/* @sources_file.txt"
       	}
