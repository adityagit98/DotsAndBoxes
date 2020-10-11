import jaco.mp3.player.MP3Player;
import java.awt.Color;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
public class DotsAndBoxes extends JFrame implements ActionListener  {    
	
	private static final long serialVersionUID = 1L;
    MP3Player mp3player = new MP3Player(getClass().getResource("musicA.mp3")); 
    public void playMusic(){
        mp3player.play();
        mp3player.setRepeat(true);
    }
    public void stopMusic(){
        mp3player.pause();
    }
    public void closeMusic(){
        mp3player.stop();
    }
	// Variables
    int difficulty;			//store difficulty level
    int width = 4;						//grid width,height
    int height = 4;
    int depth = 2;						//initial level of depth for minmax
    int depthincrease = 0;
    Player starting = Player.MIN;		//set player will make the first move
    State root;
    int[] move = new int[3];
    Error result;
    MinMax obj=new MinMax();
    public int takemove[] = new int[40]; 			//this array store the moves which are made
    public DotsAndBoxes() throws InterruptedException {
        initComponents();
        playMusic();
        for(int i =0;i<40;i++)
        {
        	takemove[i]=0;
        }
    
        root = new State(height, width, starting);		//initialization;
    
    }
    //play human function take move from human when he clicked and manipulate the array in backend
    public void playHuman(int xaxis, int yaxis){
    if(root.getType() == Player.MIN){
            result = Error.SUCCESS;
            move[0] = xaxis;
            move[1] = yaxis;
            if(result != Error.INVALID_NUMBER){
                result = root.makeMove(move[0], move[1]);
            } 
            if(result == Error.SUCCESS)
            	depthincrease++;

            if(result == Error.INVALID_SPACE)
                System.out.println("You cannot draw a line there.");
            if(result == Error.OUT_OF_BOUNDS)
                System.out.println("That coordinate is outside the boundaries of the grid.");
            if(result == Error.SPACE_FILLED)
                System.out.println("That space already has a line drawn in it.");
            String scr1 = "AI Score : "+String.valueOf(root.getMaxScore());     //store the score for printing on gui
            String scr2 = "Your Score : "+String.valueOf(root.getMinScore());
            Computer.setText(scr1);                 //print the score on the gui
            PlayerSc.setText(scr2);
        CheckWinner();                          //checkwinner() check, if game is over then who is the winnere
        if(root.getType() == Player.MAX){
            firstturn.setText("Turn -> AI(Computer)");
            playAI();                               //if player don't make a box then next chance goes to computer
            
        }
    }
}

        
    //playAI function takes move for AI
    public void playAI(){
        while(root.getType() == Player.MAX){
        	if(difficulty == 3 && depthincrease > 22)          // afterv 22 move i increase the depth for better result
        		depth+=2;
        	depthincrease++;
            move = obj.makeMove(root, depth);           //call the make move function in which AI decide the moves
            root.makeMove(move[0], move[1]);            //root.makemove function change the sate of the game according to the 
            int line = (move[0]*10)+move[1];            //best move obtained by minimax algorithm
            Draw(line);
           
            String scr1 = "AI Score : "+String.valueOf(root.getMaxScore());
            String scr2 = "Your Score : "+String.valueOf(root.getMinScore());
            Computer.setText(scr1);             //set the score to display on gui
            PlayerSc.setText(scr2);
            CheckWinner();
        }
        CheckWinner();
        firstturn.setText("Turn -> Yours");
    }
    //checkwinner function check the winner if game is over
    public void CheckWinner(){
        if(root.isOver()){              //if game is over then check winner
            if(root.getMaxScore() > root.getMinScore()){
                JOptionPane.showMessageDialog(null, "Sorry... You Lost Winner is AI");
            }
            else if(root.getMinScore() > root.getMaxScore()){
                JOptionPane.showMessageDialog(null, "Well Played... You Won..");
            }
            else{
                JOptionPane.showMessageDialog(null, "Game Draw.. Restart to Play again..");

            }
            reset();
        }
    }
    //Initialize all components
    //below are the gui parts
    private void initComponents() {

        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        level = new JComboBox<>();
        firstturn = new JLabel();
        Exit = new JButton();
        Start = new JButton();
        jLabel6 = new JLabel();
        Rules = new JButton();
        Music = new JCheckBox();
        Computer = new JLabel();
        PlayerSc = new JLabel();
        jLabel5 = new JLabel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        Board_grid = new JLabel();
        Reset = new JButton();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 5, true));
        jPanel1.setForeground(new java.awt.Color(51, 0, 153));

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 0, 153), 3, true));

        jLabel1.setFont(new java.awt.Font("Vivaldi", 1, 22));
        jLabel1.setText("DotsAndBoxes");

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 10)); 
        jLabel2.setText("18MCMC04 & 18MCMC54");
        jLabel2.setBounds(10,100,200,15);
        jPanel2.add(jLabel2);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        jLabel3.setForeground(new java.awt.Color(51, 51, 0));
        jLabel3.setText("Level");

        level.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        level.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Easy", "Medium", "Hard" }));
        level.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelActionPerformed(evt);
            }
        });
        
        Reset.setBackground(new java.awt.Color(153, 255, 153));
        Reset.setFont(new java.awt.Font("Tahoma", 1, 12)); 
        Reset.setForeground(new java.awt.Color(51, 0, 51));
        Reset.setBounds(33,180,150,40);
        jPanel2.add(Reset);
        Reset.setText("Reset");
        Reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset();
            }
        });

        firstturn.setFont(new java.awt.Font("Tahoma", 1, 14));
        firstturn.setForeground(new java.awt.Color(51, 51, 0));
        firstturn.setText("First Turn -> Yours");

        Exit.setBackground(new java.awt.Color(153, 51, 0));
        Exit.setFont(new java.awt.Font("Tahoma", 1, 12)); 
        Exit.setForeground(new java.awt.Color(153, 255, 0));
        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        Start.setBackground(new java.awt.Color(153, 255, 0));
        Start.setFont(new java.awt.Font("Tahoma", 1, 14)); 
        Start.setText("Start");
        Start.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartActionPerformed(evt);
            }
        });

        Music.setFont(new java.awt.Font("Yu Gothic Medium", 1, 10)); // NOI18N
        Music.setSelected(true);
        Music.setText("Music");
        Music.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MusicActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Vivaldi", 1, 18)); 
        jLabel6.setText("Game Rules...");

        Rules.setBackground(new java.awt.Color(153, 255, 153));
        Rules.setFont(new java.awt.Font("Tahoma", 1, 12)); 
        Rules.setForeground(new java.awt.Color(51, 0, 51));
        Rules.setText("Rules");
        Rules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RulesActionPerformed(evt);
            }
        });
        Computer.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        Computer.setForeground(new java.awt.Color(102, 0, 102));
        Computer.setText("AI Score : ");

        PlayerSc.setFont(new java.awt.Font("Times New Roman", 1, 14)); 
        PlayerSc.setForeground(new java.awt.Color(102, 0, 102));
        PlayerSc.setText("Your Score : ");

        jLabel5.setFont(new java.awt.Font("Vivaldi", 1, 19)); 
        jLabel5.setForeground(new java.awt.Color(102, 0, 102));
        jLabel5.setText("Rahul & Aditya");
        Board_grid.setOpaque(false);
        a01 = new JButton();
        a01.setBounds(39,24,83,13);
        a01.setBorderPainted(false);
        a01.setContentAreaFilled(false);
        a01.setVisible(false);
        Board_grid.add(a01);
        a03 =  new JButton();
        a03.setBounds(143,24,83,13);
        a03.setBorderPainted(false);
        a03.setContentAreaFilled(false);
        a03.setVisible(false);
        Board_grid.add(a03);
        a05 =  new JButton();
        a05.setBounds(247,24,83,13);
        a05.setBorderPainted(false);
        a05.setContentAreaFilled(false);
        a05.setVisible(false);
        Board_grid.add(a05);
        a07 =  new JButton();
        a07.setBounds(351,24,83,13);
        a07.setBorderPainted(false);
        a07.setContentAreaFilled(false);
        a07.setVisible(false);
        Board_grid.add(a07);
        a10 = new JButton();
        a10.setBounds(20,42,13,83);
        a10.setBorderPainted(false);
        a10.setContentAreaFilled(false);
        a10.setVisible(false);
        Board_grid.add(a10);
        a12 = new JButton();
        a12.setBounds(125,42,13,83);
        a12.setBorderPainted(false);
        a12.setContentAreaFilled(false);
        a12.setVisible(false);
        Board_grid.add(a12);
        a14 = new JButton();
        a14.setBounds(230,42,13,83);
        a14.setBorderPainted(false);
        a14.setContentAreaFilled(false);
        a14.setVisible(false);
        Board_grid.add(a14);
        a16 = new JButton();
        a16.setBounds(334,42,13,83);
        a16.setBorderPainted(false);
        a16.setContentAreaFilled(false);
        a16.setVisible(false);
        Board_grid.add(a16);
        a18 = new JButton();
        a18.setBounds(438,42,13,83);
        a18.setBorderPainted(false);
        a18.setContentAreaFilled(false);
        a18.setVisible(false);
        Board_grid.add(a18);
        a21 = new JButton();
        a21.setBounds(39,130,83,13);
        a21.setBorderPainted(false);
        a21.setContentAreaFilled(false);
        a21.setVisible(false);
        Board_grid.add(a21);
        a23 = new JButton();
        a23.setBounds(143,130,83,13);
        a23.setBorderPainted(false);
        a23.setContentAreaFilled(false);
        a23.setVisible(false);
        Board_grid.add(a23);
        a25 = new JButton();
        a25.setBounds(247,130,83,13);
        a25.setBorderPainted(false);
        a25.setContentAreaFilled(false);
        a25.setVisible(false);
        Board_grid.add(a25);
        a27 = new JButton();
        a27.setBounds(351,130,83,13);
        a27.setBorderPainted(false);
        a27.setContentAreaFilled(false);
        a27.setVisible(false);
        Board_grid.add(a27);
        a30 = new JButton();
        a30.setBounds(20,149,13,83);
        a30.setBorderPainted(false);
        a30.setContentAreaFilled(false);
        a30.setVisible(false);
        Board_grid.add(a30);
        a32 = new JButton();
        a32.setBounds(125,149,13,83);
        a32.setBorderPainted(false);
        a32.setContentAreaFilled(false);
        a32.setVisible(false);
        Board_grid.add(a32);
        a34 = new JButton();
        a34.setBounds(230,149,13,83);
        a34.setBorderPainted(false);
        a34.setContentAreaFilled(false);
        a34.setVisible(false);
        Board_grid.add(a34);
        a36 = new JButton();
        a36.setBounds(334,149,13,83);
        a36.setBorderPainted(false);
        a36.setContentAreaFilled(false);
        a36.setVisible(false);
        Board_grid.add(a36);
        a38 = new JButton();
        a38.setBounds(438,149,13,83);
        a38.setBorderPainted(false);
        a38.setContentAreaFilled(false);
        a38.setVisible(false);
        Board_grid.add(a38);
        a41 = new JButton();
        a41.setBounds(39,236,83,13);
        a41.setBorderPainted(false);
        a41.setContentAreaFilled(false);
        a41.setVisible(false);
        Board_grid.add(a41);
        a43 = new JButton();
        a43.setBounds(143,236,83,13);
        a43.setBorderPainted(false);
        a43.setContentAreaFilled(false);
        a43.setVisible(false);
        Board_grid.add(a43);
        a45 = new JButton();
        a45.setBounds(247,236,83,13);
        a45.setBorderPainted(false);
        a45.setContentAreaFilled(false);
        a45.setVisible(false);
        Board_grid.add(a45);
        a47 = new JButton();
        a47.setBounds(351,236,83,13);
        a47.setBorderPainted(false);
        a47.setContentAreaFilled(false);
        a47.setVisible(false);
        Board_grid.add(a47);
        a50 = new JButton();
        a50.setBounds(20,256,13,83);
        a50.setBorderPainted(false);
        a50.setContentAreaFilled(false);
        a50.setVisible(false);
        Board_grid.add(a50);
        a52 = new JButton();
        a52.setBounds(125,256,13,83);
        a52.setBorderPainted(false);
        a52.setContentAreaFilled(false);
        a52.setVisible(false);
        Board_grid.add(a52);
        a54 = new JButton();
        a54.setBounds(230,256,13,83);
        a54.setBorderPainted(false);
        a54.setContentAreaFilled(false);
        a54.setVisible(false);
        Board_grid.add(a54);
        a56 = new JButton();
        a56.setBounds(334,256,13,83);
        a56.setBorderPainted(false);
        a56.setContentAreaFilled(false);
        a56.setVisible(false);
        Board_grid.add(a56);
        a58 = new JButton();
        a58.setBounds(438,256,13,83);
        a58.setBorderPainted(false);
        a58.setContentAreaFilled(false);
        a58.setVisible(false);
        Board_grid.add(a58);
        a61 = new JButton();
        a61.setBounds(39,342,83,13);
        a61.setBorderPainted(false);
        a61.setContentAreaFilled(false);
        a61.setVisible(false);
        Board_grid.add(a61);
        a63 = new JButton();
        a63.setBounds(143,342,83,13);
        a63.setBorderPainted(false);
        a63.setContentAreaFilled(false);
        a63.setVisible(false);
        Board_grid.add(a63);
        a65 = new JButton();
        a65.setBounds(247,342,83,13);
        a65.setBorderPainted(false);
        a65.setContentAreaFilled(false);
        a65.setVisible(false);
        Board_grid.add(a65);
        a67 = new JButton();
        a67.setBounds(351,342,83,13);
        a67.setBorderPainted(false);
        a67.setContentAreaFilled(false);
        a67.setVisible(false);
        Board_grid.add(a67);
        a70 = new JButton();
        a70.setBounds(20,363,13,83);
        a70.setBorderPainted(false);
        a70.setContentAreaFilled(false);
        a70.setVisible(false);
        Board_grid.add(a70);
        a72 = new JButton();
        a72.setBounds(125,363,13,83);
        a72.setBorderPainted(false);
        a72.setContentAreaFilled(false);
        a72.setVisible(false);
        Board_grid.add(a72);
        a74 = new JButton();
        a74.setBounds(230,363,13,83);
        a74.setBorderPainted(false);
        a74.setContentAreaFilled(false);
        a74.setVisible(false);
        Board_grid.add(a74);
        a76 = new JButton();
        a76.setBounds(334,363,13,83);
        a76.setBorderPainted(false);
        a76.setContentAreaFilled(false);
        a76.setVisible(false);
        Board_grid.add(a76);
        a78 = new JButton();
        a78.setBounds(438,363,13,83);
        a78.setBorderPainted(false);
        a78.setContentAreaFilled(false);
        a78.setVisible(false);
        Board_grid.add(a78);
        a81 = new JButton();
        a81.setBounds(39,448,83,13);
        a81.setBorderPainted(false);
        a81.setContentAreaFilled(false);
        a81.setVisible(false);
        Board_grid.add(a81);
        a83 = new JButton();
        a83.setBounds(143,448,83,13);
        a83.setBorderPainted(false);
        a83.setContentAreaFilled(false);
        a83.setVisible(false);
        Board_grid.add(a83);
        a85 = new JButton();
        a85.setBounds(247,448,83,13);
        a85.setBorderPainted(false);
        a85.setContentAreaFilled(false);
        a85.setVisible(false);
        Board_grid.add(a85);
        a87 = new JButton();
        a87.setBounds(351,448,83,13);
        a87.setBorderPainted(false);
        a87.setContentAreaFilled(false);
        a87.setVisible(false);
        Board_grid.add(a87);
        a11= new JButton();
        a11.setBounds(29,33,100,100);
        a11.setVisible(false);
        Board_grid.add(a11);
        a13= new JButton();
        a13.setBounds(135,33,100,100);
        a13.setVisible(false);
        Board_grid.add(a13);
        a15= new JButton();
        a15.setBounds(241,33,100,100);
        a15.setVisible(false);
        Board_grid.add(a15);
        a17= new JButton();
        a17.setBounds(347,33,100,100);
        a17.setVisible(false);
        Board_grid.add(a17);
        a31= new JButton();
        a31.setBounds(29,140,100,100);
        a31.setVisible(false);
        Board_grid.add(a31);
        a33= new JButton();
        a33.setBounds(135,140,100,100);
        a33.setVisible(false);
        Board_grid.add(a33);
        a35= new JButton();
        a35.setBounds(241,140,100,100);
        a35.setVisible(false);
        Board_grid.add(a35);
        a37= new JButton();
        a37.setBounds(347,140,100,100);
        a37.setVisible(false);
        Board_grid.add(a37);
        a51= new JButton();
        a51.setBounds(29,247,100,100);
        a51.setVisible(false);
        Board_grid.add(a51);
        a53= new JButton();
        a53.setBounds(135,247,100,100);
        a53.setVisible(false);
        Board_grid.add(a53);
        a55= new JButton();
        a55.setBounds(241,247,100,100);
        a55.setVisible(false);
        Board_grid.add(a55);
        a57= new JButton();
        a57.setBounds(347,247,100,100);
        a57.setVisible(false);
        Board_grid.add(a57);
        a71= new JButton();
        a71.setBounds(29,354,100,100);
        a71.setVisible(false);
        Board_grid.add(a71);
        a73= new JButton();
        a73.setBounds(135,354,100,100);
        a73.setVisible(false);
        Board_grid.add(a73);
        a75= new JButton();
        a75.setBounds(241,354,100,100);
        a75.setVisible(false);
        Board_grid.add(a75);
        a77= new JButton();
        a77.setBounds(347,354,100,100);
        a77.setVisible(false);
        Board_grid.add(a77);
        a01.addActionListener(this);
        a03.addActionListener(this);
        a05.addActionListener(this);
        a07.addActionListener(this);
        a10.addActionListener(this);
        a12.addActionListener(this);
        a14.addActionListener(this);
        a16.addActionListener(this);
        a18.addActionListener(this);
        a21.addActionListener(this);
        a23.addActionListener(this);
        a25.addActionListener(this);
        a27.addActionListener(this);
        a30.addActionListener(this);
        a32.addActionListener(this);
        a34.addActionListener(this);
        a36.addActionListener(this);
        a38.addActionListener(this);
        a41.addActionListener(this);
        a43.addActionListener(this);
        a45.addActionListener(this);
        a47.addActionListener(this);
        a50.addActionListener(this);
        a52.addActionListener(this);
        a54.addActionListener(this);
        a56.addActionListener(this);
        a58.addActionListener(this);
        a61.addActionListener(this);
        a63.addActionListener(this);
        a65.addActionListener(this);
        a67.addActionListener(this);
        a70.addActionListener(this);
        a72.addActionListener(this);
        a74.addActionListener(this);
        a76.addActionListener(this);
        a78.addActionListener(this);
        a81.addActionListener(this);
        a83.addActionListener(this);
        a85.addActionListener(this);
        a87.addActionListener(this);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(Exit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(Start, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Computer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PlayerSc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(level, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Rules, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        	 .addComponent(Music, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(firstturn, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 23, Short.MAX_VALUE))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(firstturn, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Rules)
                    .addComponent(Music))
                .addGap(18, 18, 18)
                .addComponent(PlayerSc, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Computer, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Exit)
                    .addComponent(Start))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 51, 0)));

        jPanel4.setBackground(new java.awt.Color(102, 255, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        Board_grid.setIcon(new javax.swing.ImageIcon(getClass().getResource("background.jpg"))); // NOI18N
        Board_grid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 0), 1, true));
        Board_grid.setEnabled(false);
        Board_grid.setMaximumSize(new java.awt.Dimension(300, 300));
        Board_grid.setMinimumSize(new java.awt.Dimension(300, 300));
        Board_grid.addMouseListener(new java.awt.event.MouseAdapter() {
      
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Board_grid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Board_grid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }
// in below function we take the difficulty from user and set the depth for minmax search;
    private void levelActionPerformed(java.awt.event.ActionEvent evt) {

        switch (level.getSelectedIndex()) {
            case 0:
            	difficulty = 1;
                depth = 2;
                break;
            case 1:
            	difficulty = 2;
                depth = 4;
                break;
            case 2:
            	difficulty = 3;
                depth = 6;
                break;
            default:
                break;
        }
        
    }
    private void MusicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MusicActionPerformed
        if(Music.isSelected() == true){
            playMusic();
        }else{
            stopMusic();
        }
    }
    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
        
        JOptionPane.showMessageDialog(null, "Thank you for Playing....");
        System.exit(1);
    }
    //this function works after you click the start button to start the game
    private void StartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartActionPerformed
    	Board_grid.setEnabled(true);
        Start.setEnabled(false);
        level.setEnabled(false);
        firstturn.setSize(70,17);
        firstturn.setVisible(true);
        String scr1 = "AI Score : "+String.valueOf(root.getMaxScore());
        String scr2 = "Your Score : "+String.valueOf(root.getMinScore());
        Computer.setText(scr1);
        PlayerSc.setText(scr2);         //after starting the game board will be visible
 
        a01.setVisible(true);
        a03.setVisible(true);
        a05.setVisible(true);
        a07.setVisible(true);
        a10.setVisible(true);
        a12.setVisible(true);
        a14.setVisible(true);
        a16.setVisible(true);
        a18.setVisible(true);
        a21.setVisible(true);
        a23.setVisible(true);
        a25.setVisible(true);
        a27.setVisible(true);
        a30.setVisible(true);
        a32.setVisible(true);
        a34.setVisible(true);
        a36.setVisible(true);
        a38.setVisible(true);
        a41.setVisible(true);
        a43.setVisible(true);
        a45.setVisible(true);
        a47.setVisible(true);
        a50.setVisible(true);
        a52.setVisible(true);
        a54.setVisible(true);
        a56.setVisible(true);
        a58.setVisible(true);
        a61.setVisible(true);
        a63.setVisible(true);
        a65.setVisible(true);
        a67.setVisible(true);
        a70.setVisible(true);
        a72.setVisible(true);
        a74.setVisible(true);
        a76.setVisible(true);
        a78.setVisible(true);
        a81.setVisible(true);
        a83.setVisible(true);
        a85.setVisible(true);
        a87.setVisible(true);
    }

    private void RulesActionPerformed(java.awt.event.ActionEvent evt) {
        
        Information info = new Information();
        info.setVisible(true);
    }

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DotsAndBoxes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DotsAndBoxes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DotsAndBoxes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DotsAndBoxes.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }


        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new DotsAndBoxes().setVisible(true);
                } catch (InterruptedException ex) {
                    Logger.getLogger(DotsAndBoxes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Board_grid;
    private javax.swing.JButton Exit;
    public static javax.swing.JLabel Computer;
    private JCheckBox Music;
    public static JLabel PlayerSc;
    private JButton Rules;
    private JButton Start;
    private JButton Reset;
    private JLabel firstturn;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JComboBox<String> level;
    //we  can draw line on (0,1),(0,3)... so we took button here to draw possible lines.
    //and having name which include row_col.
    private JButton a01;
    private JButton a03;
    private JButton a05;
    private JButton a07;
    private JButton a10;
    private JButton a12;
    private JButton a14;
    private JButton a16;
    private JButton a18;
    private JButton a21;
    private JButton a23;
    private JButton a25;
    private JButton a27;
    private JButton a30;
    private JButton a32;
    private JButton a34;
    private JButton a36;
    private JButton a38;
    private JButton a41;
    private JButton a43;
    private JButton a45;
    private JButton a47;
    private JButton a50;
    private JButton a52;
    private JButton a54;
    private JButton a56;
    private JButton a58;
    private JButton a61;
    private JButton a63;
    private JButton a65;
    private JButton a67;
    private JButton a70;
    private JButton a72;
    private JButton a74;
    private JButton a76;
    private JButton a78;
    private JButton a81;
    private JButton a83;
    private JButton a85;
    private JButton a87;
    //after here we take the button to which use to paint the box so all the button name also incude row_col combination
    //for eaiser(like we can draw a box at 11 so first variable is a11 etc.)
    private static JButton a11;
    private static JButton a13;
    private static JButton a15;
    private static JButton a17;
    private static JButton a31;
    private static JButton a33;
    private static JButton a35;
    private static JButton a37;
    private static JButton a51;
    private static JButton a53;
    private static JButton a55;
    private static JButton a57;
    private static JButton a71;
    private static JButton a73;
    private static JButton a75;
    private static JButton a77;
    
    // End of variables declaration//GEN-END:variables
    
    //so in the actionPerformed function after clicking(for player) on the button according to button name we take the moves in backend and also in frontend
    //here we draw (GREEN) line for user.
	public void actionPerformed(ActionEvent e) {
	 
		if(e.getSource()==a01 && takemove[0] != 1)
		{
			takemove[0]=1;
			a01.setContentAreaFilled(true);
			a01.setBackground(Color.green);
			playHuman(0,1);			
			
		}
		if(e.getSource()==a03 &&takemove[1] != 1)
		{
			takemove[1]=1;
			a03.setContentAreaFilled(true);
			a03.setBackground(Color.green);
			playHuman(0,3);
			
			
		}
		if(e.getSource()==a05 && takemove[2] != 1)
		{
			takemove[2]=1;
			a05.setContentAreaFilled(true);
			a05.setBackground(Color.green);
			playHuman(0,5);
			
			
		}
		if(e.getSource()==a07 && takemove[3] != 1)
		{
			takemove[3]=1;
			a07.setContentAreaFilled(true);
			a07.setBackground(Color.green);
			playHuman(0,7);
			
		}
		
		if(e.getSource()==a10 && takemove[4] !=1)
		{
			takemove[4] =1;
			a10.setContentAreaFilled(true);
			a10.setBackground(Color.green);
			playHuman(1,0);
			
		}
		if(e.getSource()==a12 && takemove[5] !=1)
		{
			takemove[5] =1;
			a12.setContentAreaFilled(true);
			a12.setBackground(Color.green);
			playHuman(1,2);
			
			
		}
		if(e.getSource()==a14 && takemove[6] !=1)
		{
			takemove[6] =1;
			a14.setContentAreaFilled(true);
			a14.setBackground(Color.green);
			playHuman(1,4);
			
		}
		if(e.getSource()==a16 && takemove[7] !=1)
		{
			takemove[7] = 1;
			a16.setContentAreaFilled(true);
			a16.setBackground(Color.green);
			playHuman(1,6);
			
		}
		if(e.getSource()==a18 && takemove[8] != 1)
		{
			takemove[8] = 1;
			a18.setContentAreaFilled(true);
			a18.setBackground(Color.green);
			playHuman(1,8);
			
		}
		if(e.getSource()==a21 && takemove[9] != 1)
		{
			takemove[9] = 1;
			a21.setContentAreaFilled(true);
			a21.setBackground(Color.green);
			playHuman(2,1);
			
			
		}
		if(e.getSource()==a23 && takemove[10] != 1)
		{
			takemove[10] = 1;
			a23.setContentAreaFilled(true);
			a23.setBackground(Color.green);
			playHuman(2,3);
			
			
		}
		if(e.getSource()==a25 && takemove[11] != 1)
		{
			takemove[11] = 1;
			a25.setContentAreaFilled(true);
			a25.setBackground(Color.green);
			playHuman(2,5);
			
		}
		if(e.getSource()==a27 && takemove[12] != 1)
		{
			takemove[12] = 1;
			a27.setContentAreaFilled(true);
			a27.setBackground(Color.green);
			playHuman(2,7);
			
		}
		if(e.getSource()==a30 && takemove[13] != 1)
		{
			takemove[13] = 1;
			a30.setContentAreaFilled(true);
			a30.setBackground(Color.green);
			playHuman(3,0);
			
		}
		if(e.getSource()==a32 && takemove[14] != 1)
		{
			takemove[14] = 1;
			a32.setContentAreaFilled(true);
			a32.setBackground(Color.green);
			playHuman(3,2);
			
		}
		if(e.getSource()==a34 && takemove[15] != 1)
		{
			takemove[15] = 1;
			a34.setContentAreaFilled(true);
			a34.setBackground(Color.green);
			playHuman(3,4);
			
			
		}
		if(e.getSource()==a36 && takemove[16] != 1)
		{
			takemove[16] = 1;
			a36.setContentAreaFilled(true);
			a36.setBackground(Color.green);
			playHuman(3,6);
			
			
		}
		if(e.getSource()==a38 && takemove[17] != 1)
		{
			takemove[17] = 1;
			a38.setContentAreaFilled(true);
			a38.setBackground(Color.green);
			playHuman(3,8);
			
		}
		if(e.getSource()==a41 && takemove[18] != 1)
		{
			takemove[18] = 1;
			a41.setContentAreaFilled(true);
			a41.setBackground(Color.green);
			playHuman(4,1);
			
		}
		if(e.getSource()==a43 && takemove[19] != 1)
		{
			takemove[19] = 1;
			a43.setContentAreaFilled(true);
			a43.setBackground(Color.green);
			playHuman(4,3);
			;
		}
		if(e.getSource()==a45 && takemove[20] != 1)
		{
			takemove[20] = 1;
			a45.setContentAreaFilled(true);
			a45.setBackground(Color.green);
			playHuman(4,5);
			
			
		}
		if(e.getSource()==a47 && takemove[21] != 1)
		{
			takemove[21] = 1;
			a47.setContentAreaFilled(true);
			a47.setBackground(Color.green);
			playHuman(4,7);
			
		}
		if(e.getSource()==a50 && takemove[22] != 1)
		{
			takemove[22] = 1;
			a50.setContentAreaFilled(true);
			a50.setBackground(Color.green);
			playHuman(5,0);
			
		}
		if(e.getSource()==a52 && takemove[23] != 1)
		{
			takemove[23] = 1;
			a52.setContentAreaFilled(true);
			a52.setBackground(Color.green);
			playHuman(5,2);
			
		}
		if(e.getSource()==a54 && takemove[24] != 1)
		{
			takemove[24] = 1;
			a54.setContentAreaFilled(true);
			a54.setBackground(Color.green);
			playHuman(5,4);
			
		}
		if(e.getSource()==a56 && takemove[25] != 1)
		{
			takemove[25] = 1;
			a56.setContentAreaFilled(true);
			a56.setBackground(Color.green);
			playHuman(5,6);
			
		}
		if(e.getSource()==a58 && takemove[26] != 1)
		{
			takemove[26] = 1;
			a58.setContentAreaFilled(true);
			a58.setBackground(Color.green);
			playHuman(5,8);
			
		}
		if(e.getSource()==a61 && takemove[27] != 1)
		{
			takemove[27] = 1;
			a61.setContentAreaFilled(true);
			a61.setBackground(Color.green);
			playHuman(6,1);
			
		}
		if(e.getSource()==a63 && takemove[28] != 1)
		{
			takemove[28] = 1;
			a63.setContentAreaFilled(true);
			a63.setBackground(Color.green);
			playHuman(6,3);
			
		}
		if(e.getSource()==a65 && takemove[29] != 1)
		{
			takemove[29] = 1;
			a65.setContentAreaFilled(true);
			a65.setBackground(Color.green);
			playHuman(6,5);
			
		}
		if(e.getSource()==a67 && takemove[30] != 1)
		{
			takemove[30] = 1;
			a67.setContentAreaFilled(true);
			a67.setBackground(Color.green);
			playHuman(6,7);
			
		}
		if(e.getSource()==a70 && takemove[31] != 1)
		{
			takemove[31] = 1;
			a70.setContentAreaFilled(true);
			a70.setBackground(Color.green);
			playHuman(7,0);
			
		}
		if(e.getSource()==a72 && takemove[32] != 1)
		{
			takemove[32] = 1;
			a72.setContentAreaFilled(true);
			a72.setBackground(Color.green);
			playHuman(7,2);
			
		}
		if(e.getSource()==a74 && takemove[33] != 1)
		{
			takemove[33] = 1;
			a74.setContentAreaFilled(true);
			a74.setBackground(Color.green);
			playHuman(7,4);
			
		}
		if(e.getSource()==a76 && takemove[34] != 1)
		{
			takemove[34] = 1;
			a76.setContentAreaFilled(true);
			a76.setBackground(Color.green);
			playHuman(7,6);
			
		}
		if(e.getSource()==a78 && takemove[35] != 1)
		{
			takemove[35] = 1;
			a78.setContentAreaFilled(true);
			a78.setBackground(Color.green);
			playHuman(7,8);
			
		}
		if(e.getSource()==a81 && takemove[36] != 1)
		{
			takemove[36] = 1;
			a81.setContentAreaFilled(true);
			a81.setBackground(Color.green);
			playHuman(8,1);
			
		}
		if(e.getSource()==a83 && takemove[37] != 1)
		{
			takemove[37] = 1;
			a83.setContentAreaFilled(true);
			a83.setBackground(Color.green);
			playHuman(8,3);
			
		}
		if(e.getSource()==a85 && takemove[38] != 1)
		{
			takemove[38] = 1;
			a85.setContentAreaFilled(true);
			a85.setBackground(Color.green);
			playHuman(8,5);
			
		}
		if(e.getSource()==a87 && takemove[39] != 1)
		{
			takemove[39]=1;
			a87.setContentAreaFilled(true);
			a87.setBackground(Color.green);
			playHuman(8,7);			
			
		}
		
		
	}
	//to reset the game state and start from begining;
	void reset()
	{
		depthincrease = 0;
		if(difficulty == 3)
		{
			depth=6;
		}
		else if(difficulty == 2)
		{
			depth = 4;
		}
		else if(difficulty == 1)
		{
			depth = 2;
		}
		
		Board_grid.setEnabled(false);
		root.reset();
		Start.setEnabled(true);
        level.setEnabled(true);
        for(int i =0;i<40;i++)
        {
        	takemove[i]=0;
        }
        PlayerSc.setText("Your Score : ");
        Computer.setText("AI Score : ");
        a01.setBorderPainted(false);
        a01.setContentAreaFilled(false);
        a01.setVisible(false);
        a03.setBorderPainted(false);
        a03.setContentAreaFilled(false);
        a03.setVisible(false);
        a05.setBorderPainted(false);
        a05.setContentAreaFilled(false);
        a05.setVisible(false);
        a07.setBorderPainted(false);
        a07.setContentAreaFilled(false);
        a07.setVisible(false);
        a10.setBorderPainted(false);
        a10.setContentAreaFilled(false);
        a10.setVisible(false);
        a12.setBorderPainted(false);
        a12.setContentAreaFilled(false);
        a12.setVisible(false);
        a14.setBorderPainted(false);
        a14.setContentAreaFilled(false);
        a14.setVisible(false);
        a16.setBorderPainted(false);
        a16.setContentAreaFilled(false);
        a16.setVisible(false);
        a18.setBorderPainted(false);
        a18.setContentAreaFilled(false);
        a18.setVisible(false);
        a21.setBorderPainted(false);
        a21.setContentAreaFilled(false);
        a21.setVisible(false);
        a23.setBorderPainted(false);
        a23.setContentAreaFilled(false);
        a23.setVisible(false);
        a25.setBorderPainted(false);
        a25.setContentAreaFilled(false);
        a25.setVisible(false);
        a27.setBorderPainted(false);
        a27.setContentAreaFilled(false);
        a27.setVisible(false);
        a30.setBorderPainted(false);
        a30.setContentAreaFilled(false);
        a30.setVisible(false);
        a32.setBorderPainted(false);
        a32.setContentAreaFilled(false);
        a32.setVisible(false);
        a34.setBorderPainted(false);
        a34.setContentAreaFilled(false);
        a34.setVisible(false);
        a36.setBorderPainted(false);
        a36.setContentAreaFilled(false);
        a36.setVisible(false);
        a38.setBorderPainted(false);
        a38.setContentAreaFilled(false);
        a38.setVisible(false);
        a41.setBorderPainted(false);
        a41.setContentAreaFilled(false);
        a41.setVisible(false);
        a43.setBorderPainted(false);
        a43.setContentAreaFilled(false);
        a43.setVisible(false);
        a45.setBorderPainted(false);
        a45.setContentAreaFilled(false);
        a45.setVisible(false);
        a47.setBorderPainted(false);
        a47.setContentAreaFilled(false);
        a47.setVisible(false);
        a50.setBorderPainted(false);
        a50.setContentAreaFilled(false);
        a50.setVisible(false);
        a52.setBorderPainted(false);
        a52.setContentAreaFilled(false);
        a52.setVisible(false);
        a54.setBorderPainted(false);
        a54.setContentAreaFilled(false);
        a54.setVisible(false);
        a56.setBorderPainted(false);
        a56.setContentAreaFilled(false);
        a56.setVisible(false);
        a58.setBorderPainted(false);
        a58.setContentAreaFilled(false);
        a58.setVisible(false);
        a61.setBorderPainted(false);
        a61.setContentAreaFilled(false);
        a61.setVisible(false);
        a63.setBorderPainted(false);
        a63.setContentAreaFilled(false);
        a63.setVisible(false);
        a65.setBorderPainted(false);
        a65.setContentAreaFilled(false);
        a65.setVisible(false);
        a67.setBorderPainted(false);
        a67.setContentAreaFilled(false);
        a67.setVisible(false);
        a70.setBorderPainted(false);
        a70.setContentAreaFilled(false);
        a70.setVisible(false);
        a72.setBorderPainted(false);
        a72.setContentAreaFilled(false);
        a72.setVisible(false);
        a74.setBorderPainted(false);
        a74.setContentAreaFilled(false);
        a74.setVisible(false);
        a76.setBorderPainted(false);
        a76.setContentAreaFilled(false);
        a76.setVisible(false);
        a78.setBorderPainted(false);
        a78.setContentAreaFilled(false);
        a78.setVisible(false);
        a81.setBorderPainted(false);
        a81.setContentAreaFilled(false);
        a81.setVisible(false);
        a83.setBorderPainted(false);
        a83.setContentAreaFilled(false);
        a83.setVisible(false);
        a85.setBorderPainted(false);
        a85.setContentAreaFilled(false);
        a85.setVisible(false);
        a87.setBorderPainted(false);
        a87.setContentAreaFilled(false);
        a87.setVisible(false);
        a11.setVisible(false);
        a13.setVisible(false);
        a15.setVisible(false);
        a17.setVisible(false);
        a31.setVisible(false);
        a33.setVisible(false);
        a35.setVisible(false);
        a37.setVisible(false);
        a51.setVisible(false);
        a53.setVisible(false);
        a55.setVisible(false);
        a57.setVisible(false);
        a71.setVisible(false);
        a73.setVisible(false);
        a75.setVisible(false);
        a77.setVisible(false);
		
		
	}
	//Draw function draw the line for AI(RED line for AI)
	void Draw(int line)
	{
		switch(line)
		{
		
			case 01:
				a01.setBackground(Color.red);
				a01.setContentAreaFilled(true);
				takemove[0] = 1;
				break;
			case 03:
				a03.setBackground(Color.red);
				a03.setContentAreaFilled(true);
				takemove[1] = 1;
				break;
			case 05:
				a05.setBackground(Color.red);
				a05.setContentAreaFilled(true);
				takemove[2] = 1;
				break;
			case 07:
				a07.setBackground(Color.red);
				a07.setContentAreaFilled(true);
				takemove[3] = 1;
				break;
			case 10:
				a10.setBackground(Color.red);
				a10.setContentAreaFilled(true);
				takemove[4] = 1;
				break;
			case 12:
				a12.setBackground(Color.red);
				a12.setContentAreaFilled(true);
				takemove[5] = 1;
				break;
			case 14:
				a14.setBackground(Color.red);
				a14.setContentAreaFilled(true);
				takemove[6] = 1;
				break;
			case 16:
				a16.setBackground(Color.red);
				a16.setContentAreaFilled(true);
				takemove[7] = 1;
				break;
			case 18:
				a18.setBackground(Color.red);
				a18.setContentAreaFilled(true);
				takemove[8] = 1;
				break;
			case 21:
				a21.setBackground(Color.red);
				a21.setContentAreaFilled(true);
				takemove[9] = 1;
				break;
			case 23:
				a23.setBackground(Color.red);
				a23.setContentAreaFilled(true);
				takemove[10] = 1;
				break;
			case 25:
				a25.setBackground(Color.red);
				a25.setContentAreaFilled(true);
				takemove[11] = 1;
				break;
			case 27:
				a27.setBackground(Color.red);
				a27.setContentAreaFilled(true);
				takemove[12] = 1;
				break;
			case 30:
				a30.setBackground(Color.red);
				a30.setContentAreaFilled(true);
				takemove[13] = 1;
				break;
			case 32:
				a32.setBackground(Color.red);
				a32.setContentAreaFilled(true);
				takemove[14] = 1;
				break;
			case 34:
				takemove[15] = 1;
				a34.setBackground(Color.red);
				a34.setContentAreaFilled(true);
				break;
			case 36:
				takemove[16] = 1;
				a36.setBackground(Color.red);
				a36.setContentAreaFilled(true);
				break;
			case 38:
				takemove[17] = 1;
				a38.setBackground(Color.red);
				a38.setContentAreaFilled(true);
				break;
			case 41:
				takemove[18] = 1;
				a41.setBackground(Color.red);
				a41.setContentAreaFilled(true);
				break;
			case 43:
				takemove[19] = 1;
				a43.setBackground(Color.red);
				a43.setContentAreaFilled(true);
				break;
			case 45:
				takemove[20] = 1;
				a45.setBackground(Color.red);
				a45.setContentAreaFilled(true);
				break;
			case 47:
				takemove[21] = 1;
				a47.setBackground(Color.red);
				a47.setContentAreaFilled(true);
				break;
			case 50:
				takemove[22] = 1;
				a50.setBackground(Color.red);
				a50.setContentAreaFilled(true);
				break;
			case 52:
				takemove[23] = 1;
				a52.setBackground(Color.red);
				a52.setContentAreaFilled(true);
				break;
			case 54:
				takemove[24] = 1;
				a54.setBackground(Color.red);
				a54.setContentAreaFilled(true);
				break;
			case 56:
				takemove[25] = 1;
				a56.setBackground(Color.red);
				a56.setContentAreaFilled(true);
				break;
			case 58:
				takemove[26] = 1;
				a58.setBackground(Color.red);
				a58.setContentAreaFilled(true);
				break;
			case 61:
				takemove[27] = 1;
				a61.setBackground(Color.red);
				a61.setContentAreaFilled(true);
				break;
			case 63:
				takemove[28] = 1;
				a63.setBackground(Color.red);
				a63.setContentAreaFilled(true);
				break;
			case 65:
				takemove[29] = 1;
				a65.setBackground(Color.red);
				a65.setContentAreaFilled(true);
				break;
			case 67:
				takemove[30] = 1;
				a67.setBackground(Color.red);
				a67.setContentAreaFilled(true);
				break;
			case 70:
				takemove[31] = 1;
				a70.setBackground(Color.red);
				a70.setContentAreaFilled(true);
				break;
			case 72:
				takemove[32] = 1;
				a72.setBackground(Color.red);
				a72.setContentAreaFilled(true);
				break;
			case 74:
				takemove[33] = 1;
				a74.setBackground(Color.red);
				a74.setContentAreaFilled(true);
				break;
			case 76:
				takemove[34] = 1;
				a76.setBackground(Color.red);
				a76.setContentAreaFilled(true);
				break;
			case 78:
				takemove[35] = 1;
				a78.setBackground(Color.red);
				a78.setContentAreaFilled(true);
				break;
			case 81:
				takemove[36] = 1;
				a81.setBackground(Color.red);
				a81.setContentAreaFilled(true);
				break;
			case 83:
				takemove[37] = 1;
				a83.setBackground(Color.red);
				a83.setContentAreaFilled(true);
				break;
			case 85:
				takemove[38] = 1;
				a85.setBackground(Color.red);
				a85.setContentAreaFilled(true);
				break;
			case 87:
				takemove[39] = 1;
				a87.setBackground(Color.red);
				a87.setContentAreaFilled(true);
				break;
			
		}
	}
	//if the box is surrounded then fillcolor function fill the color accordingly(blue for user, orange for AI).
	//here it takes box and current as a argument where cureent denotes current player and box define where to color the box
	static void fillcolor(int box,int current)
	{
		if(box == 11)
		{
			a11.setVisible(true);
			if(current == 0)
			{
				a11.setBackground(Color.blue);
			}
			else
			{
				a11.setBackground(Color.orange);
			}
		}
		if(box == 13)
		{
			a13.setVisible(true);
			if(current == 0)
			{
				a13.setBackground(Color.blue);
			}
			else
			{
				a13.setBackground(Color.orange);
			}
		}
		if(box == 15)
		{
			a15.setVisible(true);
			if(current == 0)
			{
				a15.setBackground(Color.blue);
			}
			else
			{
				a15.setBackground(Color.orange);
			}
		}
		if(box == 17)
		{
			a17.setVisible(true);
			if(current == 0)
			{
				a17.setBackground(Color.blue);
			}
			else
			{
				a17.setBackground(Color.orange);
			}
		}
		if(box == 31)
		{
			a31.setVisible(true);
			if(current == 0)
			{
				a31.setBackground(Color.blue);
			}
			else
			{
				a31.setBackground(Color.orange);
			}
		}
		if(box == 33)
		{
			a33.setVisible(true);
			if(current == 0)
			{
				a33.setBackground(Color.blue);
			}
			else
			{
				a33.setBackground(Color.orange);
			}
		}
		if(box == 35)
		{
			a35.setVisible(true);
			if(current == 0)
			{
				a35.setBackground(Color.blue);
			}
			else
			{
				a35.setBackground(Color.orange);
			}
		}
		if(box == 37)
		{
			a37.setVisible(true);
			if(current == 0)
			{
				a37.setBackground(Color.blue);
			}
			else
			{
				a37.setBackground(Color.orange);
			}
		}
		if(box == 51)
		{
			a51.setVisible(true);
			if(current == 0)
			{
				a51.setBackground(Color.blue);
			}
			else
			{
				a51.setBackground(Color.orange);
			}
		}
		if(box == 53)
		{
			a53.setVisible(true);
			if(current == 0)
			{
				a53.setBackground(Color.blue);
			}
			else
			{
				a53.setBackground(Color.orange);
			}
		}
		if(box == 55)
		{
			a55.setVisible(true);
			if(current == 0)
			{
				a55.setBackground(Color.blue);
			}
			else
			{
				a55.setBackground(Color.orange);
			}
		}
		if(box == 57)
		{
			a57.setVisible(true);
			if(current == 0)
			{
				a57.setBackground(Color.blue);
			}
			else
			{
				a57.setBackground(Color.orange);
			}
		}
		if(box == 71)
		{
			a71.setVisible(true);
			if(current == 0)
			{
				a71.setBackground(Color.blue);
			}
			else
			{
				a71.setBackground(Color.orange);
			}
		}
		if(box == 73)
		{
			a73.setVisible(true);
			if(current == 0)
			{
				a73.setBackground(Color.blue);
			}
			else
			{
				a73.setBackground(Color.orange);
			}
		}
		if(box == 75)
		{
			a75.setVisible(true);
			if(current == 0)
			{
				a75.setBackground(Color.blue);
			}
			else
			{
				a75.setBackground(Color.orange);
			}
		}
		if(box == 77 )
		{
			a77.setVisible(true);
			if(current == 0)
			{
				a77.setBackground(Color.blue);
			}
			else
			{
				a77.setBackground(Color.orange);
			}
		}
		
	}
	
	
}
