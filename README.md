# docker_maven_plugin
# 如何使用

clone项目到本地:mvn clean install

在pom.xml配置
	
	<plugins>
		<plugin>
			<groupId>org.hush</groupId>
			<artifactId>docker-maven-plugin</artifactId>
			<version>${parent.version}</version>
			<configuration>
				<tag>hush</tag>
				<repository>registry.cn-hangzhou.aliyuncs.com/hush/basecontainer</repository>
				<dockerFilePath>${project.basedir}</dockerFilePath>
				<serviceAddress>registry.cn-hangzhou.aliyuncs.com</serviceAddress>
				<buildArgs>
					<name>${project.build.finalName}</name>
				</buildArgs>
				<useMavenSettingsForAuth>true</useMavenSettingsForAuth>
				<dockerHost>http://120.78.206.183:2375</dockerHost>
			</configuration>
		</plugin>
	</plugins>

setting.xml里面配置server

	<servers>
	  <server>
		<id>docker-hub</id>
		<username>userName</username>
		<password>xxx</password>
		<configuration>
		  <email>xxxx</email>
		</configuration>
	  </server>
	</servers>

其它使用与maven普通插件一致
