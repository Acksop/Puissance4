public class ProjetP4S{
  private static final int tailleMatriceNbPionsParColonne = 8;
  private static final int tailleMatriceNbPionsParLigne = 8;
  private static final int tailleEncadrement = 3;
  private static final int tailleAlignement = 4;

  public static void main(String[] Args){
    boolean quitter;
    quitter = false;
    do{
    String[] NomJoueurs;
    NomJoueurs = new String[2];
    Menu(NomJoueurs);
    Jeu(NomJoueurs);
    quitter = Quitter();    
    }while(!quitter);
  }
  public static void Menu(String[] Joueurs){
    //Page d'acceuil
    System.out.println("__________________________________________________________\n\n                   OOOO    OOOO   O\n                   O   O  O       O\n                   O   O  O       O\n                   OOOO    OOO    O   O\n                   O          O   OOOOO\n                   O          O       O\n                   O      OOOO        O\n\n__________________________________________________________\n                    PROJET INFORMATIQUE\n                  LICENCE 1... ROY EMMANUEL\n__________________________________________________________\n");
    System.out.println("Taille du tableau de jeu: "+tailleMatriceNbPionsParColonne+"*"+tailleMatriceNbPionsParLigne+"\nNombre de pions à aligner pour gagner: "+tailleAlignement+"\nNombre de pions à encadrer pour réussir une suppression: "+tailleEncadrement+"\n__________________________________________________________");
    System.out.print("Nom du joueur1\n?");
    Joueurs[0] = Clavier.readString();
    System.out.print("Nom du joueur2\n?");
    Joueurs[1] = Clavier.readString();
  }
  public static boolean Quitter(){
    int entree;
    entree = 0;
    do{
      System.out.print("Voulez-vous recommencez une partie (oui: 1  non: 2)\n?");
      entree = Clavier.readInt();
    }while(entree!=1 && entree != 2);
    return (entree != 1);
  }
  public static void Jeu(String[] Joueurs){
    int[][] jeu = new int[tailleMatriceNbPionsParColonne][tailleMatriceNbPionsParLigne];
    boolean[] encadrement = new boolean[7];
    int[] colonnesEncadre = new int[2];
    int tour = 0 ;
    int coupJoueColonne;
    int coupJoueLigne;
    int gagnant;
    coupJoueColonne = ChoixPlacement(jeu,tour,Joueurs);
    coupJoueLigne = AjoutPion(jeu,coupJoueColonne,tour);
    Affiche(jeu);
    tour++;
    do{
      coupJoueColonne = ChoixPlacement(jeu,tour,Joueurs);
      coupJoueLigne = AjoutPion(jeu,coupJoueColonne,tour);
      Affiche(jeu);
      EncadrementPion(jeu,coupJoueColonne,coupJoueLigne,encadrement);
      //on ne supprime et aligne des pions que si il y a encadrement
      if(encadrement[0] || encadrement[1] || encadrement[2] || encadrement[3] || encadrement[4] || encadrement[5] || encadrement[6]){
        SuppressionPion(jeu,coupJoueColonne,coupJoueLigne,encadrement,colonnesEncadre);
        AlignePions(jeu,colonnesEncadre);
        //on met les variables de jeu a -1 pour montrer que l'on a eu un encadrement, pour changer le  type de test de victoire
        coupJoueColonne = -1;
        coupJoueLigne = -1;
        System.out.println("Joli Coup "+Joueurs[tour%2]+".... Suppressions des pions adverses");
        Affiche(jeu);
      }
      gagnant = TestVictoire(jeu,coupJoueColonne,coupJoueLigne,tour);
      tour++;
      if (gagnant == 3){
    	  System.out.println("Egalité entre les deux joueurs... La partie continue!");
      }
    }while(MatricePleine(jeu) &&( gagnant == 0 || gagnant == 3));
    //on sort de la boucle si on a fini la partie ,ou si la matrice Jeu est pleine.
    if(!MatricePleine(jeu)){
        System.out.println("Pas de gagnant pour cette fois ci, le jeu est plein, il va falloir de le vider...");
    }else{
      System.out.println("Un grand bravo au vainqueur: "+Joueurs[gagnant-1]+", qui a sû démontrer sa supériorité!");
    }  
  }
  public static int ChoixPlacement(int[][] jeu,int tour,String[] Joueurs){
    int colonneJoue = 0;
    boolean colonnePleine = false;
    do{
    if(colonnePleine){
    	System.out.print("Désolé mais la colonne n°"+colonneJoue+" semble être remplie.\nAlors ");
    }
    System.out.print(Joueurs[tour%2]+" ,dans quelle colonne voulez vous jouer votre pion(nombre entre 1 et "+tailleMatriceNbPionsParLigne+") ?\n?");
    colonneJoue = NombreEntre(1,tailleMatriceNbPionsParLigne);
    colonnePleine = ColonneRemplie(jeu, colonneJoue-1);
    //on boucle dans le cas ou la colonne choisie est pleine
    }while(colonnePleine);
    return colonneJoue-1;
  }
  public static int NombreEntre(int nbmin , int nbmax){
	System.out.print("?");
    int nb;
    do{
    	System.out.print("?");
    	nb = Clavier.readInt();
    	System.out.print("\n");
    }while(nb<nbmin || nb>nbmax);
    return nb;
  }  
  public static boolean ColonneRemplie(int[][] jeu, int colonne){
    if(jeu[0][colonne]==0){
      return false;
    }else{
      return true;
    }
  }
  public static boolean MatricePleine(int[][] jeu){
	  int i = 0;
	  while(i<tailleMatriceNbPionsParLigne && ColonneRemplie(jeu,i)){
		  i++;
	  }
	  if (i==tailleMatriceNbPionsParLigne){
		  return false;
	  }else{
		  return true;
	  }
  }
  public static int AjoutPion(int[][] jeu,int colonne,int tour){
	  int i=0;
	  do{
		  i++;
	  }while(jeu[tailleMatriceNbPionsParColonne-i][colonne]!=0);
	  jeu[tailleMatriceNbPionsParColonne-i][colonne]=tour%2+1;
	  return(tailleMatriceNbPionsParColonne-i);
  }
  public static void Affiche(int[][] jeu){
	  	for(int i=1;i<=tailleMatriceNbPionsParLigne;i++){
	  		System.out.print("\t"+i);
	  	}
	  	System.out.print("\n");
	  	for(int i=0;i<=tailleMatriceNbPionsParLigne*9;i++){
	  		System.out.print("_");
	  	}
	  	System.out.print("\n");
	  	for(int i=0;i<tailleMatriceNbPionsParColonne;i++){
	  		System.out.print("|\t");
	  		CroixRond(jeu,i,0);
	  		for(int j=1;j<tailleMatriceNbPionsParLigne;j++){
	  			System.out.print("\t");
	  			CroixRond(jeu,i,j);
	  		}
	  		System.out.print("\t|\n");
	  	}
  }
  public static void CroixRond(int[][] jeu,int ligne, int colonne){
	  switch(jeu[ligne][colonne]){
	  	case 0:
	  		System.out.print(".");
	  		break;
	  	case 1:
	  		System.out.print("O");
	  		break;
	  	case 2:
	  		System.out.print("X");
	  		break;
	  }
  } 
  public static void EncadrementPion(int[][] jeu,int colonne,int ligne,boolean[] encadrement){
	  //encadrement vers le haut
	  encadrement[0] = Encadre(jeu,ligne,colonne,1,0);
	  //encadrement sur la gauche
	  encadrement[1] = Encadre(jeu,ligne,colonne,0,-1);
	  //encadrement sur la droite
	  encadrement[2] = Encadre(jeu,ligne,colonne,0,1);
	  //encadrement diagonale hautGauche
	  encadrement[3] = Encadre(jeu,ligne,colonne,-1,-1);
	  //encadrement diagonale hautDroite
	  encadrement[4] = Encadre(jeu,ligne,colonne,-1,1);
	  //encadrement diagonale basGauche
	  encadrement[5] = Encadre(jeu,ligne,colonne,1,-1);
	  //encadrement diagonale basDroite
	  encadrement[6] = Encadre(jeu,ligne,colonne,1,1);	  
  }
  public static boolean Encadre(int[][] jeu, int ligne, int colonne,int pasLigne,int pasColonne){
	  boolean encadre = false;
	  //condition pour ne pas sortir des limites du tableau
	  if((((ligne+(tailleEncadrement+1)*pasLigne)<tailleMatriceNbPionsParColonne && (ligne+(tailleEncadrement+1)*pasLigne)>=0)&&((colonne+(tailleEncadrement+1)*pasColonne)<tailleMatriceNbPionsParLigne && (colonne+(tailleEncadrement+1)*pasColonne)>=0))){
		  //condition de départ pour un encadrement: si les pions extérieurs sont identiques.
		  if(jeu[ligne][colonne]==jeu[ligne+(tailleEncadrement+1)*pasLigne][colonne+(tailleEncadrement+1)*pasColonne]){
				int i=pasColonne;
				int j=pasLigne;
				while((i<(tailleEncadrement+1) && i>-(tailleEncadrement+1))&&(j<(tailleEncadrement+1) && j>-(tailleEncadrement+1)) && jeu[ligne+j][colonne+i]!=jeu[ligne][colonne] && jeu[ligne+j][colonne+i]!=0){
					i += pasColonne;
					j += pasLigne;
				}
				if(i==(tailleEncadrement+1) || i==-(tailleEncadrement+1) || j==(tailleEncadrement+1) || j==-(tailleEncadrement+1)){
					encadre = true;
				}
		  }
	  }
	  return encadre;
  }
  public static void SuppressionPion(int[][] jeu, int coupJoueColonne, int coupJoueLigne, boolean[] encadrement, int[] colonnesEncadre){
	  //on initialise les variables avec la colonne qui a été jouée,résultat: on la rangera quand même; bien qu'elle ne doit etre rangée que lorsqu'il y a un alignement vertical.
	  colonnesEncadre[0] = coupJoueColonne;
	  colonnesEncadre[1] = coupJoueColonne;
	  if(encadrement[0]){
		  for(int i=coupJoueLigne+1;i<=coupJoueLigne+tailleEncadrement;i++){
			  jeu[i][coupJoueColonne]=0;
		  }
	  }
	  if(encadrement[1]){
		  for(int i=coupJoueColonne-1;i>=coupJoueColonne-tailleEncadrement;i--){
			  jeu[coupJoueLigne][i]=0;
		  }
		  colonnesEncadre[0] = min(colonnesEncadre[0],coupJoueColonne-tailleEncadrement);
		  colonnesEncadre[1] = max(colonnesEncadre[1],coupJoueColonne-1);
	  }
	  if(encadrement[2]){
		  for(int i=coupJoueColonne+1;i<=coupJoueColonne+tailleEncadrement;i++){
			  jeu[coupJoueLigne][i]=0;
		  }
		  colonnesEncadre[0] = min(colonnesEncadre[0],coupJoueColonne+1);
		  colonnesEncadre[1] = max(colonnesEncadre[1],coupJoueColonne+tailleEncadrement);
	  }
	  if(encadrement[3]){
		  for (int i=1;i<=tailleEncadrement;i++){
			  jeu[coupJoueLigne-i][coupJoueColonne-i]=0;
		  }
		  colonnesEncadre[0] = min(colonnesEncadre[0],coupJoueColonne-tailleEncadrement);
		  colonnesEncadre[1] = max(colonnesEncadre[1],coupJoueColonne-1);
	  }
	  if(encadrement[4]){
		  for (int i=1;i<=tailleEncadrement;i++){
			  jeu[coupJoueLigne-i][coupJoueColonne+i]=0;
		  }
		  colonnesEncadre[0] = min(colonnesEncadre[0],coupJoueColonne+1);
		  colonnesEncadre[1] = max(colonnesEncadre[1],coupJoueColonne+tailleEncadrement);
	  }
	  if(encadrement[5]){
		  for (int i=1;i<=tailleEncadrement;i++){
			  jeu[coupJoueLigne+i][coupJoueColonne-i]=0;
		  }
		  colonnesEncadre[0] = min(colonnesEncadre[0],coupJoueColonne-tailleEncadrement);
		  colonnesEncadre[1] = max(colonnesEncadre[1],coupJoueColonne-1);
	  }
	  if(encadrement[6]){
		  for (int i=1;i<=tailleEncadrement;i++){
			  jeu[coupJoueLigne+i][coupJoueColonne+i]=0;
		  }
		  colonnesEncadre[0] = min(colonnesEncadre[0],coupJoueColonne+1);
		  colonnesEncadre[1] = max(colonnesEncadre[1],coupJoueColonne+tailleEncadrement);
	  }
  }
  public static int max(int a, int b){
	  if(a>b){
		  return a;
	  }else{
		  return b;
	  }
  }
  public static int min(int a, int b){
	  if(b>a){
		  return a;
	  }else{
		  return b;
	  }
  }
  public static void AlignePions(int[][] jeu,int[] colonnesEncadre){
	  for(int i=colonnesEncadre[0];i<=colonnesEncadre[1];i++){
		  AligneColonne(jeu,i);  
	  }
  }
  public static void AligneColonne(int[][] jeu, int colonne){
	  int i = tailleMatriceNbPionsParColonne-1;
	  int indiceInsertion = 0;
	  boolean insertion = false;
	  while(i>=0){
		  if(jeu[i][colonne]==0 && !insertion){
			  indiceInsertion = i;
			  insertion = true;
		  }else if(jeu[i][colonne]!=0 && insertion){
			  insertion = false;
			  jeu[indiceInsertion][colonne] = jeu[i][colonne];
			  jeu[i][colonne] = 0;
			  i=indiceInsertion;
		  }
		  i--;
	  }
  }
  public static int TestVictoire(int[][] jeu,int colonne,int ligne,int tour){
	  if (colonne == -1 && ligne == -1){
		  //test dans toute la matrice après un encadrement.
		  return AlignementDansMatrice(jeu);
	  }else{
		  boolean alignement = false;
		  //test vertical a partir de la position du pion qui a été jouée
		  if(ComptageAligne(jeu,ligne,colonne,-1,0)){
			 alignement = true;
		  }
		  //test horizontal
		  if(ComptageAligne(jeu,ligne,colonne,0,1)){
				 alignement = true;
		  }
		  //test diagonale bas/gauche et haut/droite
		  if(ComptageAligne(jeu,ligne,colonne,-1,-1)){
				 alignement = true;
		  }
		  //test diagonale bas/droite et heut/gauche
		  if(ComptageAligne(jeu,ligne,colonne,1,-1)){
				 alignement = true;
		  }
		  if(alignement){
			  return tour%2+1;
		  }else{
			  return 0;
		  }
	  }
  }
  public static boolean ComptageAligne(int[][] jeu,int ligne,int colonne,int pasL,int pasC){
	  int i = ligne;
	  int j = colonne;
	  int compteur = 0;
	  while(((i<tailleMatriceNbPionsParColonne && i>=0)&&(j<tailleMatriceNbPionsParLigne && j>=0))&& jeu[i][j]==jeu[ligne][colonne] && jeu[i][j]!=0){
		  compteur++;
		  i+=pasL;
		  j+=pasC;
	  }
	  //on verifie si il y a un alignement de 4, ou plus ,pions dans une seule direction,
	  //sinon on teste dans la direction opposé en comptabilisant ceux déjà alignés
	  if(compteur<tailleAlignement){
		  i=ligne-pasL;
		  j=colonne-pasC;
		  while(((i<tailleMatriceNbPionsParColonne && i>=0)&&(j<tailleMatriceNbPionsParLigne && j>=0))&& jeu[i][j]==jeu[ligne][colonne] && jeu[i][j]!=0){
			  compteur++;
			  i-=pasL;
			  j-=pasC;
		  }
		  if (compteur>=tailleAlignement){
			  return true;
		  }else{
			  return false;
		  }
	  }else{
		  return true;
	  }
  }
  public static int AlignementDansMatrice(int[][] jeu){
	  int compteurVictoireJ1 = 0;
	  int compteurVictoireJ2 = 0;
	  //test vertical
	  for(int i=0;i<=tailleMatriceNbPionsParColonne-tailleAlignement;i+=tailleAlignement){
		  for(int j=0;j<=tailleMatriceNbPionsParLigne-1;j++){
			  if(ComptageAligne(jeu,i,j,1,0)){
				  if(jeu[i][j]==1){
					  compteurVictoireJ1++;
				  }else{
					  compteurVictoireJ2++;
				  }
			  }
		  }
	  }
	  //test horizontal
	  for(int i=0;i<=tailleMatriceNbPionsParColonne-1;i++){
		  for(int j=0;j<=tailleMatriceNbPionsParLigne-tailleAlignement;j+=tailleAlignement){
			  if(ComptageAligne(jeu,i,j,0,1)){
				  if(jeu[i][j]==1){
					  compteurVictoireJ1++;
				  }else{
					  compteurVictoireJ2++;
				  }
			  }
		  }
	  }
	  //test diagonale
	  for(int i=0;i<=tailleMatriceNbPionsParColonne-tailleAlignement;i+=tailleAlignement){  
		  for(int j=0;j<=tailleMatriceNbPionsParLigne-1;j++){
			  //diagonale haut/gauche et bas/droite
			  if(ComptageAligne(jeu,i,j,1,-1)){
				  if(jeu[i][j]==1){
					  compteurVictoireJ1++;
				  }else{
					  compteurVictoireJ2++;
				  }
			  }
			  //diagonale bas/gauche et haut/droite
			  if(ComptageAligne(jeu,i,j,-1,-1)){
				  if(jeu[i][j]==1){
					  compteurVictoireJ1++;
				  }else{
					  compteurVictoireJ2++;
				  }
			  }
		  }  
	  }
	  if(compteurVictoireJ1 == compteurVictoireJ2){
		   if(compteurVictoireJ1==0){
			   return 0;
		   }else{
			   return 3;
		   }
	  }else if(compteurVictoireJ1 > compteurVictoireJ2){
		  return 1;
	  }else{
		  return 2;
	  }
  }
}