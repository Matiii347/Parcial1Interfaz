Proyecto: Simulador de Ecosistema - 1° Instancia Evaluativa 🌿🐰🐺
Este proyecto consiste en un Simulador de Ecosistema por turnos desarrollado en Java, diseñado para ejecutarse a través de la terminal. El objetivo es modelar las interacciones biológicas entre diferentes entidades (Plantas, Conejos y Lobos) bajo la influencia de climas variables y la intervención del usuario.
Este trabajo fue realizado para la materia Interfaz Gráfica (Mayo 2026) y aplica conceptos avanzados de Programación Orientada a Objetos (POO).

✨ Características Principales
Simulación Dinámica: Ciclos de vida que incluyen alimentación, reproducción, envejecimiento y muerte.  
Sistema de Clima: Afecta directamente el rendimiento de las especies (Soleado, Lluvioso, Sequía e Invierno).  
Intervención del Jugador: Cada 3 turnos, el usuario puede alterar el ecosistema agregando entidades o cambiando el clima.  
Reporte Final: Estadísticas detalladas sobre nacimientos, muertes, longevidad y causas del colapso o finalización.

🛠️ Tecnologías y Conceptos de POO Aplicados
El desarrollo integra los siguientes pilares de la programación:  
Herencia y Clases Abstractas: Uso de una clase base Entidad y una capa intermedia Animal.  
Interfaces: Implementación de Reproducible y Mortal para definir comportamientos específicos de forma polimórfica.  
Polimorfismo: Procesamiento de colecciones de objetos mediante interfaces y clases base.  
Encapsulamiento: Atributos privados con validación estricta en Getters y Setters.  
Sobrecarga: Métodos con múltiples firmas (ej. agregarEntidad).

🚀 Instrucciones de Ejecución
Lenguaje: Java.
IDE Recomendado: NetBeans (Versión compatible con el proyecto).  
Librerías: No requiere librerías externas (Standard JDK).
Ejecución: Clonar el repositorio y ejecutar la clase principal para iniciar el menú de configuración en consola.

📂 Estructura del Proyecto
Entidad (Abstracta): Base para todas las formas de vida.  
Animal (Abstracta): Especialización para seres que se mueven y comen.  
Planta, Conejo, Lobo: Clases concretas con lógicas de interacción únicas.  
Ecosistema: Motor central que gestiona los turnos y las listas de entidades.  
Interfaces: Reproducible y Mortal.

🧠 Uso de IA y Documentación
De acuerdo a las normativas de la evaluación, el uso de herramientas de IA fue transparente y documentado:  
Carpeta de Documentación: /docs (Contiene capturas de prompts e historiales).  
Links a conversaciones: [Enlace a ChatGPT/Gemini/etc.].  
Desafíos: [Menciona aquí un conflicto técnico breve, ej: manejo de ConcurrentModificationException en las colecciones y cómo se resolvió].

📺 Video Explicativo
Puedes ver la defensa técnica del proyecto y la explicación del código aquí:
👉 [Link al Video (Vimeo/Canva)]

Fecha de entrega: 08 de Mayo de 2026.
