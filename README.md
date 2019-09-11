# los-ponys
- configurar java 8 o > (variables de entorno en el ide o en el archivo o interface del sistema operativo)
- bajar tomcat y tenerlo en una carpeta a mano cosa de cuando haya que configurarlo en el ide, lo referencies a esa carpeta
- instalar node js para usar npm, manejador de dependencias de javascript
- verificar que se haya instalado bien con npm -v y node -v
- parado en el root del proyecto correr: npm install --save (esto va bajar todas las dependecias que esten en el package.json)
- Una vez que haya instalado todo correr: npm run webpack (esto buildea todos los assests javascript y los transforma a un javascript que entienda el navegador)
- Una vez levantado todo, si se hacen cambios en el controller, servicios, etc hay que restartear el tomcat o redeployar la aplicacion java, es muy facil, el intellij lo ofrece.
- Si metemos cosas con javascript, mientras trabajamos, en la consola hay que correr: npm run webpack -- --watch (Esto es para que webpack se quede escuchando los cambios de los archivos y siempre este actualizado)

