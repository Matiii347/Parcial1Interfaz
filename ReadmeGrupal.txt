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
1er Parcial:Implementación de la clase base abstracta Entidad.
Desarrollo de la clase Planta.
2do Parcial:Empaquete todo,hice el controlador y los Daos.
Tuvo complicado con el tema de los dao porq no me daba cuenta pero se pudo,el controlador fue un poco mas facil.

Manuel Molinero:

2 Parcial: 
Implementacion y creacion de la pantalla inicial, osea el simulador de ecosistema que permita al usuario configurar las condiciones iniciales de la simulación antes de ejecutarla.

Esta fue mi conversación con ChatGPT para el proyecto: https://chatgpt.com/c/6a30653c-b2ac-83e9-8269-6298329dd29d

1 Parcial:
Implementación de la clase abstracta Animal.

Desarrollo de la clase concreta Conejo.

Durante la realización del proyecto me encargué de desarrollar la lógica de Animal como base para los distintos animales del ecosistema y de implementar el comportamiento del Conejo, principalmente en los métodos de acción, alimentación y reproducción.

Lo que me resulto más complicado del proyecto fue entender la interacción entre las clases y cómo debían comunicarse con Ecosistema. Por ejemplo, dentro de Conejo, métodos como comer() y actuar() necesitan interactuar con el ecosistema para recorrer listas de plantas, verificar recursos disponibles y registrar eventos.

Esta fue mi conversación con ChatGPT para el proyecto: https://chatgpt.com/share/69ffe67e-b4b8-83e9-889e-9e10de51d439

Valentin Gonzalez:
Parcial 2:
Participe en la implementacion y diseño de la segunda vista sumado a su comportamiento y funcionalidades.

En esta segunda etapa hubo varias cosas que probe por primera vez pero considero que la mayor dificultad que tuve fue principalmente con la creacion y manejo de la grilla, principalmente porque se me complicaba el hecho de generar en cada turno una grilla distinta para mostrar visualmente los cambios de posiciónes de los seres del ecosistema.

Esta fue mi conversación con Gemini para el proyecto: https://share.gemini.google/7kBAMrhbGUgg

Parcial 1:
Implementación de la clase concreta Lobo.

Definición de la Interfaz Reproducible para el comportamiento de cría.

Durante la realización del proyecto me encargue de hacer las Clases Lobo, la Interface Reproducible y parte del main.
Personalmente lo mas complicado del proyecto para mi fue entender la lógica general y como es que actuaban las clases entre si, por ejemplo:
 Dentro de lobo los métodos Actuar() y Comer() necesitan interactuar con ecosistema para sacar la ArrayList de conejo para modificar un conejo individual o confirmar que haya conejos vivos en la lista.

Esta fue mi conversación con Gemini para el proyecto: https://gemini.google.com/share/8bbf8f6dbedd

Sacha Saleski:

Definición de la Interfaz Mortal para la lógica de fallecimiento.

Desarrollo de la clase Ecosistema (gestión de ciclos y contenedores).

Clase "Ecosistema" e Interface "Mortal"
Interfaz grafica
simulador de ecosistema - java
------>proyecto desarrollado para la 1ra instancia evaluativa
	El programa simula un ecosistema por turnos donde interactuan plantas, conejos y lobos bajo diferentes condiciones climaticas.
	Las funcionalidades principales son: 
	simulacion por turnos: control total del ciclo de vida de las entidades (nacer, comer, reproducirse y morir).
	sistema de clima: efectos reales de soleado, lluvioso, sequia e invierno que afectan la energia y reproduccion.  
	intervencion: cada 3 turnos el usuario puede cambiar el clima o agregar nuevas entidades. 
	reporte final: estadisticas detalladas sobre muertes, nacimientos, longevidad y eventos maximos.
------>Personalmente me trabe un rato en el Metodo procesarTurno() ya que era el motor de la simulacion y habia que pensarlo bastante. 
"Mortal" lo pude desarrollar con mas facilidad ya que la complejidad era menor.
------>conceptos de poo aplicados 
		Herencia y clases abstractas: uso de una clase base entidad y una intermedia animal. 
		Interfaces y polimorfismo: implementacion de reproducible y mortal para gestionar comportamientos comunes de forma eficiente.  
		Encapsulamiento: todos los atributos son privados con sus respectivos getters y setters validados. 
		Sobrecarga: metodos para agregar entidades con diferentes parametros de entrada.  
------>Adjunto mi charla con la ia, use Gemini: https://gemini.google.com/share/0eb02163b5e6

2DA INSTANCIA EVALUATIVA:
desarrollo de la capa vista (panelreporte.java), logica de reportes, historial de conteos e integracion con el controlador.  
 arquitectura del proyecto (mvc + dao)el proyecto se encuentra estructurado en cuatro capas totalmente independientes para evitar la mezcla de responsabilidades: 
 modelo: contiene las clases base de la simulacion (entidad, animal, planta, conejo, lobo, ecosistema) junto con las interfaces reproducible y mortal. 
 dao (data access object): encargado de la lectura y escritura del estado del ecosistema (entidades, turnos, clima) en archivos .txt de flujo plano sin conocer la vista ni el controlador. 
 controlador: actua como puente. recibe las peticiones de la vista, ejecuta las acciones sobre el modelo, interactua con el dao y actualiza la interfaz sin contener codigo de swing.  
vista: diseñada con netbeans swing. contiene los jframes, paneles y componentes visuales. los listeners solo delegan acciones al controlador sin procesar logica de negocio.  
componentes swing utilizadosjmenubar: menu superior para la gestion de la simulacion y acceso directo a los reportes.
jtabbedpane: usado en la pantalla de reportes para separar de forma limpia el resumen general del historial estadistico.jtable & jscrollpane: tabla dinamica envuelta en scroll para listar la evolucion de las poblaciones turno a turno. 
jlabel: etiquetas para mostrar contadores, clima y turnos en tiempo real.  
joptionpane: cuadros de dialogo para confirmar acciones criticas y alertas de colapso del sistema.  
bonus implementadosvisualizacion de estadisticas en tiempo real: panel que registra y muestra el historial de conteos demograficos turno a turno, calculando de manera automatica los picos minimos y maximos de cada poblacion con su respectivo turno de ocurrencia. 
Desarrollo del Main (Lógica Global)
La clase Main fue desarrollada de manera colaborativa por todo el equipo. Debido a que esta clase integra todas las partes (Ecosistema, Animales y Plantas), trabajamos en conjunto para resolver las complicaciones surgidas con la ejecución de los ciclos de vida y la interacción de las entidades dentro del ecosistema.


VIDEO
https://drive.google.com/drive/folders/1A9GxJIoVLE6qyRDYI4ZzXYU1i6QfSff7

FIGMA=https://www.figma.com/make/u0vbt80AFGKpNg29387FwO/Ecosystem-Simulation-App?t=sZzXzn1SRoDkYrcM-20&fullscreen=1
