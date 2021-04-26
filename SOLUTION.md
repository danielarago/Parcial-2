# Solución parcial corte 2

## I. Implementación

Evidencia de funcionamiento de las pruebas en `ProjectTest`:

![image](https://user-images.githubusercontent.com/78309222/115090553-c9c32280-9eda-11eb-92d0-5f845369548f.png)


## II. Diseñando

El diseño propuesto para el método `summarize` en la clase `Project`. Primero se realiza la especificación:

        /**
        * Present a summary of the duration of a project.
        * 
        * @return String, a list with the summary of the duration of the project provided by ExecutiveSynthesizer and the summary of each student provided by StudentSynthesizer. 
        **/
        public String summarize(){

        }

Diagrama de secuencia:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/summarize()%20Diagrama%20de%20secuencia.png)

Diagrama de clases actualizado: 

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/SabanaResearch.png)

Especificación métodos adicionales identificados:

        /**
        * Gets the summary of the project through ExecutiveSynthesizer.
        * 
        * @return String, goal and duration of project
        **/
        public String getProjectSummary(){

        }

    
        /**
        * Gets the summary of all the students through StudentSynthesizer.
        * 
        * @return String, names and durations in project of all students involved
        **/
        public String getStudentsSummary(){

        }

        /**
        * Gets the summary of each student through StudentSynthesizer, with their name and duration in String form.
        * 
        * @return String, name and duration in project of student
        **/
        public String getStudentSummary(){
            
        }

        /**
        * Gets the duration of a student in a specific project.
        * 
        * @return Duration, time the student spent in that project.
        **/
        public Duration getDurationInProject(){
            
        }
    
# Corrección

Nueva especificación propuesta para summarize en `Project`:

        /**
        * Gets a summary of a project through a synthesizer
        *
        * @return String with summary
        **/
        public String summarize(){

        }

Diagrama de secuencia para summarize en `StudentSynthesizer`:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/summarize()%20StudentSynthesizer%20Diagrama%20de%20secuencia.png)

Diagrama de secuencia para summarize en `ExecutiveSynthesizer`:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/summarize()%20ExecutiveSynthesizer%20Diagrama%20de%20secuencia.png)

Nuevo diagrama de clases completado:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/Diagrama%20de%20clases%20SabanaResearch.png)

Nuevas especificaciones de métodos adicionales. En `Student`:

        /**
        * Gets the total duration of all of a student's assigned activities
        *
        * @return Duration of a Student's assigned activities
        **/
        public Duration getActivitiesDuration(){

        }

En `ISynthesizer`:

        /**
        * Begins the process of a synthesizer
        *
        * @param List of the type of Object the synthesizer has to summarize
        * @return String with the characteristics specific to each type of synthesizer
        **/
        public abstract String synthesize(List<Object>);


## III. Extiendiendo

Cambios propuestos al diagrama de clases general:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/SabanaResearchNew.png)

En cuanto a los diseños propuestos en partes anteriores quizás se debería generar un nuevo tipo de error al sacar la duración que se lanze si las dependencias de una actividad no hay terminado y la actividad no ha podido empezar. Además tendría que existir una manera de validar si la duración de una dependencia ha terminado para poder implementar esa funcionalidad.

En cuanto a los métodos en sí realmente no tendrían que cambiar mucho. El diseño no era malo para las condiciones que existían, sin embargo si debería tener algo de flexibilidad en caso de que surjan nuevas condiciones. La gran ventaja fue que ya se habían implementado excepciones, entonces con unas adiciones a la clase customizada de excepciones hay maneras de manejar nuevas excepciones en la lógica del negocio. 

# Corrección

Cambios nuevos propuestos al diagrama de clases general:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/Extendiendo%20Diagrama%20de%20clases%20SabanaResearch.png)

Los nuevos diseños requieren que el método de getDuration de una actividad cambie para incluir la duración de sus dependencias. Además tendría que considerarse la condición de que una dependencia haya terminado para que una actividad se considere como activa.

El diseño requería algo de flexibilidad ya que sin haber integrado el código del parcial anterior, no existía manera de determinar si una actividad estaba activa y tocaría generar algún método para implementar la nueva lógica en cuanto a la necesidad de las dependencias de estar terminadas para poder comenzar una actividad y tenerla activa. 

## IV. Conceptos

1. ¿Cuáles son las acciones los tres momentos importantes de las excepciones? ¿Cuál es el objetivo de cada una? ¿Cómo se implementa en Java cada acción?

Los tres momentos más importantes de las excepciones son:
* _Throw_: Cuando se lanza la excepción.
* _Propagate_: Cuando se propaga la excepción, relegando la responsabilidad a la parte del código que pueda manejarla y haciendo que el compilador pueda predecir la excepción, evitando errores por unchecked exceptions.
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

Para evitar que se sobre-escriba un método este tendría que ser final, de esta manera el método está definido para todas las subclases de la misma manera.

# V. Bono

Se implementó el bono de los sintetizadores.

Screenshots de nuevas pruebas:

![image](https://user-images.githubusercontent.com/78309222/116013966-83677500-a5f8-11eb-82e0-03b144f10dcc.png)


# Solución parcial corte 1

## I. Diagrama de memoria

![Diagrama de memoria](https://github.com/danielarago/Parcial-1/blob/master/Diagrama%20de%20memoria.png)

El diagrama de memoria también se encuentra en los archivos.

## II. Código

Diagrama de clases actualizado, también se encuentra en los archivos.

![Diagrama de clases](https://github.com/danielarago/Parcial-1/blob/master/Diagrama%20de%20clases%20modificado.png)

## III. Diseño

Diagrama de secuencia del diseño

![Diagrama de secuencia diseño](https://github.com/danielarago/Parcial-1/blob/master/Dise%C3%B1o%20Parcial.png)

Corrección del diseño según lo visto en clase. Se vuelve más fácil el proceso, buscando tanto al estudiante como al grupo dentro de SabanaResearch y después entrando a registrar al estudiante. 

![Diagrama de secuencia corregido](https://github.com/danielarago/Parcial-1/blob/master/Dise%C3%B1o%20Parcial%20corregido.png)

## IV. Conceptos

* ¿Qué es encapsulamiento? ¿Qué ventajas ofrece?

    * El encapsulamiento es un mecanismo que une tanto los datos como el estado de un objeto, es decir sus atributos y sus métodos, en una sola unidad lógica. Sus ventajas son que hace que los datos de un objeto solo tengan que ser comprendidos por ese objeto, y la integridad de los datos es responsabilidad solamente de ese objeto. Cualquier cambio en los datos solo va a a afectar al objeto en cuestión entonces si hay errores más adelante es fácil identificarlos y solucionarlos. El encapsulamiento trata cada objeto como una fortaleza, protegiendo sus datos y haciendo que la única manera de utilizarlos en áreas del código cliente sea pidiéndolos. Por lo tanto, un dato que tenga que cambiar solo se debe cambiar en su origen, en su objeto, y va a estar actualizado para el resto del código, ahorrando tiempo y recursos. 

* ¿Qué es ocultación de información? ¿Por qué aplicarla? ¿Cómo se implementa en Java?

    * Al igual que el encapsulamiento es una práctica del paradigma de programación orientada a objetos que protege integridad de datos. Hace que se pueda modificar el acceso del código de una clase por fuera de ella, para que algunas cosas sean privadas y solo sean asequibles dentro de la clase misma y otras públicas. Generalmente, los métodos son de acceso libre y los atributos de acceso privado, aunque hay unas pocas excepciones. Esto se debe a varias razones, en primer lugar no es necesario que el código cliente vea los atributos de un objeto o sus datos, sino que vea sus servicios. En segundo lugar, al restringir el acceso a los datos se puede enforzar una lógica de negocio para ellos. Si fueran de libre acceso otras clases podrían llegar a introducir datos en ciertos atributos que no tengan sentido en el contexto. Pero si el atributo es privado y solo se puede modificar con un método set entonces le objeto tiene control y puede mantener lógica. En Java se implementan con los modificadores de acceso public y private que se declaran al principio de los métodos y los atributos.

## V. Bono

Diagrama de secuencia para la nueva funcionalidad:

![image](https://github.com/danielarago/Parcial-2/blob/master/TEST-SECOND-SABANA-RESEARCH-1.6.0/diagrams/countInactiveProjects()%20Diagrama%20de%20secuencia.png)

Se implementa la funcionalidad de contar las actividades, las iteraciones, proyectos y finalmente grupos inactivos. Además, se incluyo una línea en los tests existentes de SabanaResearchTest que probara el método de contar projectos inactivos, y por lo tanto todos los que se llaman por dentro.

Screenshot de pruebas funcionando:

![image](https://user-images.githubusercontent.com/78309222/116009745-e6e5a880-a5e0-11eb-87bc-36f228eb14e9.png)

## Configuración SonarQube

Se puede ver en el código que se intentó realizar el análisis en SonarQube, sin embargo, nunca conectó con el proyecto creado. Se deja evidencia de hasta donde se realizó el proceso ya que aunque se siguen todas las instrucciones dadas, no funcionó.
