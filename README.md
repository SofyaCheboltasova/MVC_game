# Task:
- Develop a computer game. The main requirement is that the game must be converted to a multiplayer game with shared data.
- The architecture of the program should be based on the MVC (Model-View-Controller) pattern.

## Program Requirements:
- The game must support a high score table.
- The following commands should be available to the user: Exit, About, New Game, High Scores.

<img width="846" alt="image 8" src="https://user-images.githubusercontent.com/96617834/179721029-401acaee-012e-4127-80e0-e1ba2fca4609.png">

<img width="846" alt="image 9" src="https://user-images.githubusercontent.com/96617834/179721094-306d55ad-c6f1-472e-bb06-e3ecf46cbc33.png">


## Здадание №1
1. Для чего нужен CSS?  
   CSS нужен для описания внешнего вида страницы, для оформления различных ее элементов. Если HTML задает структуру веб-страницы, то c помощью CSS эта структура затем будет стилизована.  

2. Какие способы подключения стилей ты знаешь?
  - **Атрибут style**  
    Находится непосредственно внутри HTML тега, к которому нужно применить стиль  

  - **Тег <style></style>**  
    Внутри него описываются стили. Располагается в HTML файле  

  - **Подключение внешнего файла с расширением .css**   
    Подключается с помощью тега <link>, где href - путь к файлу со стилями, а rel имеет значение stylesheet  

3. Как определить какое пространство на странице займёт элемент?  
   - **Блочный элемент**  
      - Блочные элементы по умолчанию занимают всю доступную им ширину и высоту своего содержимого.  
      - Если указано свойство ***border-box***:
         - = content-box — значение по умолчанию, соответствует стандартной блочной модели.
            Для подсчета ширины / высоты блока нужно сложить значение width / height с имеющимися внутренними отступами(padding), внешними отступами(margin) и границами(boarder), предварительно умножив границы на 2.  

         - = border-box — в ширину включаются поля и рамки.
            Ширина / высота блока = width / height

  2. **Строчный элемент**  
    Строчным элементам нельзя задать высоту и ширину: они всегда имеют размеры содержимого.
