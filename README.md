# Ejercicio - Ficheros XML y JSON
## Descripción del ejercicio
Se creará un fichero XML y otro fichero JSON para poder anotar la compra del supermercado. Ambas listas deben contener
la misma información.

### Objetivos
* Crear 2 listas, una en XML y otra en JSON que contengan toda la información que se requiere.
* Que la aplicación sea capaz de pasar los test de Maven.

### Lista de la compra
#### Artículos
* 2 → Manzana
* 1 → Leche
* 3 → Pan
* 2 → Huevo
* 1 → Queso
* 1 → Cereal
* 4 → Agua
* 6 → Yogur
* 2 → Arroz
#### XML
* El fichero se llamará ``shoppingList.xml`` y estará ubicado en ``src/main/resources``
* La primera línea del fichero XML tiene que contener esta información (*obligatoria en los ficheros xml*):
  ```xml
  <?xml version="1.0" encoding="UTF-8" standalone="no"?>
  ```
* La lista debe tener un nodo raíz llamado `shoppinglist`
* Del nodo `shoppinglist` debe colgar otro nodo llamado `items`, del que a su vez cuelgan múltiples nodos `item`
* Cada nodo item tiene un atributo `quantity` que contendrá el número de elementos de la lista que tendrá cada artículo
* Como texto de cada nodo `item`, será el artículo que queremos indicar.
#### JSON
* El fichero se llamará ``shoppingList.json`` y estará ubicado en ``src/main/resources``
* La lista debe tener un elemento raíz.
* Dicho elemento tendrá un atributo llamado ``items``, que contendrá un array de elementos (*componentes*)
* Cada uno de estos elementos tendrá 2 atributos, el primero con la clave ``quantity`` que contendrá un número entero
  que servirá para indicar el número de artículos de ese tipo y otro con la clave ``text``, que contendrá el nombre del
  artículo.
### Requisitos
* No se pueden modificar en absoluto las clases de test.
### Test

```
  mvn test
```