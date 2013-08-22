require 'buildr/groovy'
Buildr::Groovy::Groovyc::REQUIRES.groovy = '1.7.5'
repositories.remote << 'http://repo1.maven.org/maven2'

define 'codenarc' do
  project.group = 'org.codenarc'
  project.version = '0.19'
  compile.from('src/main/java').with 'log4j:log4j:jar:1.2.13', 'org.gmetrics:GMetrics:jar:0.5', 'org.codehaus.groovy:groovy-all:jar:1.7.5'
  compile.from('src/main/groovy').using(:groovyc).with 'junit:junit:jar:4.8.2'
  package :jar
end

