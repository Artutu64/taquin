# taquin
Projet de résolution du taquin grâce à l'algo A*

# Optimisation
J'ai fais des modifications le 28 novembre afin de rendre le programme plus rapide. Cela ne concerne que les classes suivantes:
PuzzleNode -> Ajout de la méthode equals
J'ai ensuite reexporté le projet et l'ai commit sur github.

# Utilisation
1. Téléchargez le fichier taquin.jar
2. Utilisez la commande java -jar <NomDuFichier>.jar avec le format suivant:

       Format de la commande: java -jar <nomFichier>.jar <distance> <a> <b> <c> <d> <e> <f> <g> <h> <i>
    
       distance <==> 0 = 'distance de Hamming' / 1 = 'distance de manhatan'
       a <==> 'random' randomise la grille de départ
      
       Matrice de départ
      
        a b c
        d e f
        g h i
       
       Veuillez noter qu'il faut mettre un 0 a la place vide !

# Consultation du code
Le contenu de l'archive .jar contenant les fichier .class est présent dans le dossier cytech de github.
