# Jolograms

API que permite crear hologramas en servidores 1.7, 1.8 y 1.7.10 ProtocolHack.

Modo de uso:

1. Creamos una instancia

```java
Jologram jologram = JologramBuilder
      .newInstance(JavaPlugin)    //Su JavaPlugin.
      .renderDistance(10)         //Distancia de renderizado (Fuera de esta el holograma desaparece).
      .hologramIndentation(0.2)   //Separación de las lineas.
      .checkTime(1)               //Periodo de tiempo en el que se revisara la distancia a los hologramas.
      .build();                   //Crea la instancia.
```


2. Construimos un holograma
```java
Hologram holgram = jologram
      .createHologram()           //Iniciamos la creación del holograma
      .location(Location)         //Localización del holograma
      .lines("line1", "line2")    //Lineas del holograma
      .build();                   //Construimos el holograma
```
3. Agregamos o quitamos visualizadores
```java
hologram.addPlayer(Player)          //Agregamos a un jugador
hologram.removePlayer(Player)       //Quitamos un jugador
```
4. Tenemos metodos para obtener información de nuestro holograma
```java
hologram.getWatchers()              //Nos devuelve los jugadores con acceso al holograma
hologram.getLocation()              //Localización del holograma
```
5. Luego tenemos metodos que nos ayudaran a modificar nuestro holograma sobre la marcha
```java
hologram.setLines(List<String>)     //Establece las lineas del holograma
hologram.setLine(int, String)       //Cambia el texto de una linea
hologram.removeLine(int)            //Elimina una linea
hologram.clear()                    //Elimina todas las lineas
```

Eso es todo lo que necesitas saber para usar esta API
