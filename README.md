1. create project using maven archetype webapp
2. download tomcat and run it locally -> https://www.youtube.com/watch?v=eFKANue4YYY
3. install tomcat pulgin for deploying war file 
4. The SmartTomcat will auto load the Webapp classes and libs from project and module, You needn't copy the classes and libs to the WEB-INF/classes and WEB-INF/lib. The Smart Tomcat plugin will auto config the classpath for tomcat server.
5. the goal was to first get the barebone setup ready. 
6. how can I get get this tomcat on EC2. 
7. instead setup up a database loally , I decied to use the database on the aws to save time. 
8. creating the mysql , I went with the easy create option in aws
9. set the public access to true so I can connect to the db. 
   

***************
Endpoint
mysql-1.c1qck2mgy2c3.us-east-2.rds.amazonaws.com

# issues:
tomcat already running background. port already used. learned how to should down in windows thorgh terminal and services GUI in windows 
to enable public access I had to add my ip address to the securty inbound rules
