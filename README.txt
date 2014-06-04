FalconSVD
=========

Sistema para reconocimiento de rostros mediante la Descomposición de Valores Singulares SVD, nombre clave Falcon.


Los pasos para ejecutar el proyecto son:

1. Se elige el objetivo a buscar dentro de la base de datos. Para esto, entramos en File -> Open Target y buscamos la imagen objetivo.

2. opcionalmente, podemos modificar el umbral o valor de tolerancia desde edit -> Threshold.

3. Se determina la base de datos de la cual determinaremos si la imagen esta o no. Para esto entramos en Edit -> Select DB -> Somebody y seleccionamos la carpeta de un individuo. Si se desea se puede seleccional Edit -> Select DB -> People y seleccionamos la carpeta para un grupo de personas.

4. Luego vamos al menu Process -> Media para determinar la imagen media de la base de datos.

5. A continuacion, damos click en Process -> normal, para determinar la cara normalizada que se pretende encontrar. 

6. Se hace click en Process -> FalconSVD -> Make para realizar las operaiones pertinentes.

7. Se hace click en Process -> FalconSVD -> Detection para determinar si el rostro se encuentra dentro de la imagen media o no.

8. Se hace click en Process -> FalconSVD -> Recognition para mostrar que tan cercana se encontraba la imagen objetivo dentro del conjunto de entrenamiento.

