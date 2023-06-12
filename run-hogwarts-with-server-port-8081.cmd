export JAVA_HOME=/home/serge/.jdks/corretto-11.0.18 -

mvn clean install
@REM mvn install -DskipTests

@REM java -jar target/hogwarts-school-0.0.1-SNAPSHOT.jar --spring.profiles.active=server-port-8081
java -jar -Dspring.profiles.active=server-port-8081 target/hogwarts-school-0.0.1-SNAPSHOT.jar