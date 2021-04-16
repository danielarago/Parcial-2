# Solución parcial corte 2

## II. Diseñando

El diseño propuesto para el método `summarize` en la clase `Project`:

    

        /**
        * Present a summary of the duration of a project.
        * 
        * @return String, a list with the summary of the duration of the project provided by ExecutiveSynthesizer and the summary of each student provided by StudentSynthesizer.
        **/
        public String summarize(){

        }



## III. Extiendiendo

## IV. Conceptos

1. ¿Cuáles son las acciones los tres momentos importantes de las excepciones? ¿Cuál es el objetivo de cada una? ¿Cómo se implementa en Java cada acción?

Los tres momentos más importantes de las excepciones son:
* _Throw_: Cuando se lanza la excepción.
* _Propagate_: Cuando se propaga la excepción, relegando la responsabilidad a la parte del código que pueda manejarla y haciendo que el compilador puede predecir la excepción, evitando errores por unchecked exceptions.
* _Catch_: Cuando se captura la excepción. Aquí se le da manejo a la excepción.

En Java se implementan de la siguiente manera: 
* _Throw_: Se utiliza la palabra clave throw dentro del área del código donde se tenga que lanzar la excepción bajo las condiciones necesarias.
* _Propagate_: En la cabecera, o el header, de todos los métodos a los cuales les pueda salir la excepción se pone:

    ```

        methodHeader () throws exceptionType {

        }

    ```

* _Catch_: La parte del código que puede lanzar excepción se pone en un bloque de código de try y en el bloque o los bloques siguientes de catch se hace manejo de la excepción o de las excepciones que pueden surgir, generalmente enviando el mensaje correspondiente de la excepción.

2. ¿Qué es sobre-escritura de métodos? ¿Por qué aplicarla? ¿Cómo impedir que se sobre-escriba un método?
La sobre-escritura de métodos es lo que sucede cuando en una clase se hereda un método o concreto o abstracto, tanto por herencia como por implementación de una interfaz. La clase que recibe este método sobre-escribe el método con @Override para que realize la función que necesite. En la herencia permite que las clases descendientes a una clase todas tengan un método que haga más o menos lo mismo pero cumpliendo con las características específicas de cada una. En la implementación de interfaces permite que los métodos abstractos puedan ser instanciados y se les pueda dar un cuerpo, enfuerza que tenga que existir ese método para todas las clases que implementen la interfaz. 

Para evitar que se sobre-escriba un método este tendría que ser privado.


## V. Bono
