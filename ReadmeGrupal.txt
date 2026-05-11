Charla con la IA(Funes)
https://gemini.google.com/share/64c6b29b4980
https://chatgpt.com/share/6a00e91c-ae20-83e9-bed3-10b98f583e18
https://gemini.google.com/share/c5ff8ad45120

Trabajo Práctico: Simulación de Ecosistema
Integrantes:

Funes, Matias

Gonzalez, Valentin

Molinero, Manuel

Saleski, Sacha

Descripción del Proyecto
Este proyecto consiste en una simulación de un ecosistema desarrollado en Java, aplicando conceptos de Programación Orientada a Objetos (POO) como herencia, interfaces, polimorfismo y manejo de colecciones. El sistema modela la interacción entre diferentes entidades (plantas y animales) dentro de un entorno controlado.

Reparto de Responsabilidades y Arquitectura
Para la organización del código, nos dividimos el desarrollo de las clases y lógica de la siguiente manera:

Matias Funes:

Implementación de la clase base abstracta Entidad.

Desarrollo de la clase Planta.

Manuel Molinero:

Implementación de la clase abstracta Animal.

Desarrollo de la clase concreta Conejo.

Durante la realización del proyecto me encargué de desarrollar la lógica de Animal como base para los distintos animales del ecosistema y de implementar el comportamiento del Conejo, principalmente en los métodos de acción, alimentación y reproducción.

Lo que me resulto más complicado del proyecto fue entender la interacción entre las clases y cómo debían comunicarse con Ecosistema. Por ejemplo, dentro de Conejo, métodos como comer() y actuar() necesitan interactuar con el ecosistema para recorrer listas de plantas, verificar recursos disponibles y registrar eventos.

Esta fue mi conversación con ChatGPT para el proyecto: https://chatgpt.com/share/69ffe67e-b4b8-83e9-889e-9e10de51d439

Valentin Gonzalez:

Implementación de la clase concreta Lobo.

Definición de la Interfaz Reproducible para el comportamiento de cría.

Durante la realización del proyecto me encargue de hacer las Clases Lobo, la Interface Reproducible y parte del main.
Personalmente lo mas complicado del proyecto para mi fue entender la lógica general y como es que actuaban las clases entre si, por ejemplo:
 Dentro de lobo los métodos Actuar() y Comer() necesitan interactuar con ecosistema para sacar la ArrayList de conejo para modificar un conejo individual o confirmar que haya conejos vivos en la lista.

Esta fue mi conversación con Gemini para el proyecto: https://gemini.google.com/share/8bbf8f6dbedd

Sacha Saleski:

Definición de la Interfaz Mortal para la lógica de fallecimiento.

Desarrollo de la clase Ecosistema (gestión de ciclos y contenedores).



Desarrollo del Main (Lógica Global)
La clase Main fue desarrollada de manera colaborativa por todo el equipo. Debido a que esta clase integra todas las partes (Ecosistema, Animales y Plantas), trabajamos en conjunto para resolver las complicaciones surgidas con la ejecución de los ciclos de vida y la interacción de las entidades dentro del ecosistema.




