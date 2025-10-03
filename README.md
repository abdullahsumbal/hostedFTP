
# HostedFTP Coding Test – Setup Notes

The web app is hosted on a custom HTTPS domain.
https://hostedftp.abdullahsumbal.com/login.jsp

## Local Setup

1. **Create Maven Webapp Project**

    * Used `mvn archetype:generate` with the `maven-archetype-webapp` template to get the basic structure in place.

2. **Tomcat Local**

    * Downloaded Tomcat and ran it locally.
    * Followed this YouTube guide: [https://www.youtube.com/watch?v=eFKANue4YYY](https://www.youtube.com/watch?v=eFKANue4YYY)
    * Installed **SmartTomcat** plugin in IntelliJ so I can run/deploy directly without copying JARs into `WEB-INF/lib`.
    * Goal was just to get a barebone JSP/Servlet running first.

---

## Database (AWS RDS)

3. **Skip Local DB**

    * To save time, I skipped setting up MySQL locally and went straight to AWS RDS.

4. **Create MySQL**

    * Used the **Easy Create** option in AWS RDS to spin up a MySQL instance.
    * Set **public access = true** (just to connect quickly).
    * Added my IP to the RDS security group inbound rules so I could connect from local machine.

5. **Passwords**

    * Looked up the right way to handle passwords → **salt + hash**.
    * Followed this guide: [https://www.youtube.com/watch?v=zt8Cocdy15c](https://www.youtube.com/watch?v=zt8Cocdy15c)

6. **First User**

    * To add the first password, I created a one-off helper in Java to generate the salt+hash and inserted it into the `users` table.

---

## Implementation

7. **Code Pieces**

    * Created DTOs, entity, DB connection helper, JSP pages.
    * Added login flow → user submits credentials → validate against DB → show welcome page with user data.

8. **JDBC Config**

    * First tried putting DB connection in Tomcat’s local `conf/context.xml` but had issues.
    * Moved the config into the project itself, worked fine.
    * Finally got login working end-to-end.

---

## Deploy on EC2

9. **EC2 Setup**

    * Created Ubuntu EC2 instance.
    * Installed **Java 21** and **Tomcat 11**.
    * Added MySQL connector JAR into Tomcat so JDBC works.

   ### Commands I ran on EC2:

   ```bash
   # Update packages
   sudo apt update && sudo apt upgrade -y

   # Install Java 21
   sudo apt install -y openjdk-21-jdk

   # Download Tomcat 11
   wget https://dlcdn.apache.org/tomcat/tomcat-11/v11.0.0-M22/bin/apache-tomcat-11.0.0-M22.tar.gz

   # Extract to /opt
   sudo mkdir -p /opt/tomcat
   sudo tar xzvf apache-tomcat-11.0.0-M22.tar.gz -C /opt/tomcat --strip-components=1

   # Make scripts executable
   sudo chmod +x /opt/tomcat/bin/*.sh

   # Add MySQL connector
   wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-j-8.4.0.tar.gz
   tar xzvf mysql-connector-j-8.4.0.tar.gz
   sudo cp mysql-connector-j-8.4.0/mysql-connector-j-8.4.0.jar /opt/tomcat/lib/

   # Start Tomcat
   /opt/tomcat/bin/startup.sh
   ```

    * Tomcat runs by default on **port 8080**.

10. **Security**

* Opened **8080** in the EC2 security group so the app is accessible.
* To allow DB access, added the EC2 security group inside the RDS security group inbound rules.

11. **Deploy WAR**

* Packaged the app:

  ```bash
  mvn clean package
  ```
* Copied WAR to EC2:

  ```bash
  scp -i "transfer.pem" ./target/codingtest.war ubuntu@<ipv4>:/tmp/
  ```
* Moved into Tomcat webapps:

  ```bash
  sudo mv /tmp/codingtest.war /opt/tomcat/webapps/
  sudo chown ubuntu:ubuntu /opt/tomcat/webapps/codingtest.war
  ```

12. **Test**

* Open in browser:

  ```
  http://<EC2_PUBLIC_IP>:8080/codingtest
  ```

---

## Deployment & Access

* The WAR (`codingtest.war`) is deployed on an **EC2 Ubuntu instance** running **Tomcat 11 + Java 21**.
* The app connects to an **AWS RDS MySQL instance** for user authentication.
* Initially the app was running directly on the EC2 public hostname:

  ```
  http://ec2-3-150-201-177.us-east-2.compute.amazonaws.com:8080/codingtest/login.jsp
  ```
* To make it cleaner, I added a **custom domain + HTTPS** using Nginx as a reverse proxy in front of Tomcat.
* Now the app can be accessed here:

  ```
  https://hostedftp.abdullahsumbal.com/login.jsp
  ```

This shows the login page, and on successful login redirects to `welcome.jsp`.

Adding the custom domain was not required for the coding test but I did it as an extra touch to make the project feel closer to production.

---

## Random Gotchas

* Tomcat was already running in the background → port already used. Learned how to shut it down in Windows via terminal and Services GUI.
* Couldn’t connect to RDS at first → fixed by allowing EC2 security group inside the RDS security group inbound rules.
* Exposing RDS publicly worked for quick setup, but better approach later would be to remove public access and only allow EC2.
* Use ELastic IP for EC2 so the IP doesn’t change on reboot.
* Nginx config was tricky → had to ensure proxying to the right port and context path.
## Security Cleanup (TODOs)

* Right now, port 8080 (Tomcat) is still open to the world.

* Since Nginx is in front handling 80/443, 8080 should be restricted to local only.

* Update EC2 security group → remove inbound 8080, allow only 80/443.

* RDS was initially created with Public Access = true.

* Better approach is to disable public access and only allow inbound traffic from the EC2 security group.