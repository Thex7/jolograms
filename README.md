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

