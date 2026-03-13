package view;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javafx.scene.media.MediaPlayer;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import model.Colour;
import model.card.Card;
import model.card.standard.Queen;
import model.card.standard.Seven;
import model.card.standard.Standard;
import model.card.standard.Suit;
import model.card.standard.Ten;
import model.player.Marble;
import model.player.Player;
import engine.Game;
import engine.board.Cell;
import exception.GameException;
import exception.InvalidCardException;
import exception.InvalidMarbleException;
import exception.SplitOutOfRangeException;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class GameGUI extends Application implements EventHandler<ActionEvent> {
	private Game game;

	private MediaPlayer mediaPlayer;
	private MediaPlayer Endgame;

	private HBox bottomPanel;
	private HBox homeZone1;
	private VBox safeZone1;

	private VBox rightPanel;
	private VBox homeZone2;
	private HBox safeZone2;

	private HBox topPanel;
	private HBox homeZone3;
	private VBox safeZone3;

	private VBox leftPanel;
	private VBox homeZone4;
	private HBox safeZone4;

	private HBox track1;
	private VBox track2;
	private HBox track3;
	private VBox track4;

	@SuppressWarnings("unused")
	private Label Sc_P0;
	private Label Sc_P1;
	private Label Sc_P2;
	private Label Sc_P3;

	private VBox firepit;
	private VBox deck;
	private Label firePitLabel;
	private Label decklabel;
	private Label trapCellLabel;
	private HBox humanplayer_hand;
	private Label currentplayerlabel;
	private Label nextplayerlabel;
	private Button deselectAll;
	private Button skipturn;
	private Button CardsInfo;

	private int turn = 0; 
	private Label turnLabel;
	private String[] lastPlayedCards = new String[4]; 
	private ArrayList<Button> trackButtons;
	private ArrayList<Button> cardsButtons;

	private Stage primstage; 

	public static final Image RED;
	public static final Image YELLOW;
	public static final Image GREEN;
	public static final Image BLUE;
	public static final Image EMPTY;
	public static final Image Aceclub;
	public static final Image AceDiamond;
	public static final Image Aceheart;
	public static final Image Acespade;
	public static final Image twoclub;
	public static final Image twoDiamond;
	public static final Image twoheart;
	public static final Image twospade;
	public static final Image threeclub;
	public static final Image threeDiamond;
	public static final Image threeheart;
	public static final Image threespade;
	public static final Image fourclub;
	public static final Image fourDiamond;
	public static final Image fourheart;
	public static final Image fourspade;
	public static final Image fiveclub;
	public static final Image fiveDiamond;
	public static final Image fiveheart;
	public static final Image fivespade;
	public static final Image sixclub;
	public static final Image sixDiamond;
	public static final Image sixheart;
	public static final Image sixspade;
	public static final Image sevenclub;
	public static final Image sevenDiamond;
	public static final Image sevenheart;
	public static final Image sevenspade;
	public static final Image eightclub;
	public static final Image eightDiamond;
	public static final Image eightheart;
	public static final Image eightspade;
	public static final Image nineclub;
	public static final Image nineDiamond;
	public static final Image nineheart;
	public static final Image ninespade;
	public static final Image tenclub;
	public static final Image tenDiamond;
	public static final Image tenheart;
	public static final Image tenspade;
	public static final Image jackclub;
	public static final Image jackDiamond;
	public static final Image jackheart;
	public static final Image jackspade;
	public static final Image queenclub;
	public static final Image queenDiamond;
	public static final Image queenheart;
	public static final Image queenspade;
	public static final Image kingclub;
	public static final Image kingDiamond;
	public static final Image kingheart;
	public static final Image kingspade;
	public static final Image MarbleBurner;
	public static final Image MarbleSaver;

	public static final Image Back = new Image(GameGUI.class.getResourceAsStream("/sources/Back.png"));
	public static final Image Back2 = new Image(GameGUI.class.getResourceAsStream("/sources/Back2.png"));
	Runnable processTurn = () -> {
		try {
			if (game.canPlayTurn()) {
				game.playPlayerTurn();

				Player currentPlayer = game.getPlayers().get(turn);

				Card playedCard = currentPlayer.getSelectedCard();
				if (playedCard != null) {
					lastPlayedCards[turn] = playedCard.getName();
				} else {
					lastPlayedCards[turn] = "No card played";
				}
			}
			game.endPlayerTurn();
			gameUpdate();

		} catch (GameException e) {
			displayAlert("Error", e.getMessage());

			String res = "";
			for (int i = 0; i < game.getPlayers().get(turn).getHand().size(); i++) {
				res+= game.getPlayers().get(turn).getHand().get(i).getName();
			}
			displayAlert("Error", e.getMessage()+ res + " " + e.getCause());

		} catch (Exception ev) {
			System.err.println("Unexpected error during turn for " + game.getPlayers().get(turn).getName() + ": " + ev.getMessage());
			game.endPlayerTurn();
			gameUpdate();
		}
	};
	static {
		RED = new Image(GameGUI.class.getResourceAsStream("/sources/red.png"));
		YELLOW = new Image(GameGUI.class.getResourceAsStream("/sources/yellow.png"));
		GREEN = new Image(GameGUI.class.getResourceAsStream("/sources/green.png"));
		BLUE = new Image(GameGUI.class.getResourceAsStream("/sources/blue.png"));
		EMPTY = new Image(GameGUI.class.getResourceAsStream("/sources/empty.png"));

		Aceclub= new Image(GameGUI.class.getResourceAsStream("/sources/C1.png"));
		AceDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D1.png"));
		Aceheart= new Image(GameGUI.class.getResourceAsStream("/sources/H1.png"));
		Acespade= new Image(GameGUI.class.getResourceAsStream("/sources/S1.png"));
		twoclub= new Image(GameGUI.class.getResourceAsStream("/sources/C2.png"));
		twoDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D2.png"));
		twoheart= new Image(GameGUI.class.getResourceAsStream("/sources/H2.png"));
		twospade= new Image(GameGUI.class.getResourceAsStream("/sources/S2.png"));
		threeclub= new Image(GameGUI.class.getResourceAsStream("/sources/C3.png"));
		threeDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D3.png"));
		threeheart= new Image(GameGUI.class.getResourceAsStream("/sources/H3.png"));
		threespade= new Image(GameGUI.class.getResourceAsStream("/sources/S3.png"));
		fourclub= new Image(GameGUI.class.getResourceAsStream("/sources/C4.png"));
		fourDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D4.png"));
		fourheart= new Image(GameGUI.class.getResourceAsStream("/sources/H4.png"));
		fourspade= new Image(GameGUI.class.getResourceAsStream("/sources/S4.png"));
		fiveclub= new Image(GameGUI.class.getResourceAsStream("/sources/C5.png"));
		fiveDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D5.png"));
		fiveheart= new Image(GameGUI.class.getResourceAsStream("/sources/H5.png"));
		fivespade= new Image(GameGUI.class.getResourceAsStream("/sources/S5.png"));
		sixclub= new Image(GameGUI.class.getResourceAsStream("/sources/C6.png"));
		sixDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D6.png"));
		sixheart= new Image(GameGUI.class.getResourceAsStream("/sources/H6.png"));
		sixspade= new Image(GameGUI.class.getResourceAsStream("/sources/S6.png"));
		sevenclub= new Image(GameGUI.class.getResourceAsStream("/sources/C7.png"));
		sevenDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D7.png"));
		sevenheart= new Image(GameGUI.class.getResourceAsStream("/sources/H7.png"));
		sevenspade= new Image(GameGUI.class.getResourceAsStream("/sources/S7.png"));
		eightclub= new Image(GameGUI.class.getResourceAsStream("/sources/C8.png"));
		eightDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D8.png"));
		eightheart= new Image(GameGUI.class.getResourceAsStream("/sources/H8.png"));
		eightspade= new Image(GameGUI.class.getResourceAsStream("/sources/S8.png"));
		nineclub= new Image(GameGUI.class.getResourceAsStream("/sources/C9.png"));
		nineDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D9.png"));
		nineheart= new Image(GameGUI.class.getResourceAsStream("/sources/H9.png"));
		ninespade= new Image(GameGUI.class.getResourceAsStream("/sources/S9.png"));
		tenclub= new Image(GameGUI.class.getResourceAsStream("/sources/C10.png"));
		tenDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/D10.png"));
		tenheart= new Image(GameGUI.class.getResourceAsStream("/sources/H10.png"));
		tenspade= new Image(GameGUI.class.getResourceAsStream("/sources/S10.png"));
		jackclub= new Image(GameGUI.class.getResourceAsStream("/sources/CJack.png"));
		jackDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/DJack.png"));
		jackheart= new Image(GameGUI.class.getResourceAsStream("/sources/HJack.png"));
		jackspade= new Image(GameGUI.class.getResourceAsStream("/sources/SJack.png"));
		queenclub= new Image(GameGUI.class.getResourceAsStream("/sources/CQueen.png"));
		queenDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/DQueen.png"));
		queenheart= new Image(GameGUI.class.getResourceAsStream("/sources/HQueen.png"));
		queenspade= new Image(GameGUI.class.getResourceAsStream("/sources/SQueen.png"));
		kingclub= new Image(GameGUI.class.getResourceAsStream("/sources/CKing.png"));
		kingDiamond= new Image(GameGUI.class.getResourceAsStream("/sources/DKing.png"));
		kingheart= new Image(GameGUI.class.getResourceAsStream("/sources/HKing.png"));
		kingspade= new Image(GameGUI.class.getResourceAsStream("/sources/SKing.png"));
		MarbleBurner= new Image(GameGUI.class.getResourceAsStream("/sources/marbleBurner.png"));
		MarbleSaver= new Image(GameGUI.class.getResourceAsStream("/sources/marbleSaver.png"));
	}

	@Override
	public void start(Stage primaryStage) {
		// Initial scene: Name input
		BorderPane root = new BorderPane();
		Text TITLE = new Text("sign in to Jackaroo Game :)");
		TITLE.setStyle("-fx-fill: white; -fx-font-size: 16; -fx-font-weight: bold;");

		primaryStage.setTitle("sign in");
		root.setStyle("-fx-background-color: lavender;");

		try {
			Image icon = new Image(getClass().getResourceAsStream("/sources/icon.png"));
			primaryStage.getIcons().add(icon);
		} catch (Exception e) {
			System.err.println("Failed to load icon: " + e.getMessage());
		}

		try {
			String backgroundImage = getClass().getResource("/sources/wallpaper.png").toExternalForm();
			root.setStyle("-fx-background-image: url('" + backgroundImage + "'); " +
					"-fx-background-position: center center; " +
					"-fx-background-repeat: stretch; " +
					"-fx-background-size: cover;");
		} catch (Exception e) {
			System.err.println("Failed to load background image: " + e.getMessage());
			root.setStyle("-fx-background-color: lavender;");
		}
		TextField nameEnter = new TextField();
		nameEnter.setPromptText("Enter your name: ");
		nameEnter.setStyle("-fx-prompt-text-fill: gray;");
		nameEnter.setMaxWidth(200);

		Label statusLabel = new Label("");
		statusLabel.setStyle("-fx-text-fill: red;");
		EventHandler<ActionEvent> submitAction = event -> {
			String playerName = nameEnter.getText().trim();
			try {
				if (playerName.isEmpty()) {
					throw new IllegalArgumentException("Player name cannot be empty");
				}
				game = new Game(playerName);
				statusLabel.setText("Welcome, " + playerName + "!");

				statusLabel.setStyle("-fx-text-fill: green;" +
						"-fx-background-color: rgba(255, 255, 255, 0.8); " +
						"-fx-padding: 5px; " +
						"-fx-background-radius: 5px;");
				PauseTransition pause = new PauseTransition(Duration.seconds(1));
				pause.setOnFinished(e -> {
					statusLabel.setText("");
					statusLabel.setStyle("-fx-text-fill: red;");
					start_gameStage(playerName, primaryStage);
				});
				pause.play();
			} catch (IllegalArgumentException e) {
				statusLabel.setText("Please enter a valid name.");
				statusLabel.setStyle("-fx-text-fill: red; " +
						"-fx-background-color: rgba(255, 255, 255, 0.8); " +
						"-fx-padding: 5px; " +
						"-fx-background-radius: 5px;");
			} catch (IOException e) {
				statusLabel.setText("Failed to initialize game. Please try again.");
				statusLabel.setStyle("-fx-text-fill: red; " +
						"-fx-background-color: rgba(255, 255, 255, 0.8); " +
						"-fx-padding: 5px; " +
						"-fx-background-radius: 5px;");
			}
		};

		Button submitButton = new Button("Click to START");
		submitButton.setOnAction(submitAction);
		nameEnter.setOnAction(submitAction);

		VBox centerBox = new VBox(10);
		centerBox.setAlignment(Pos.CENTER);
		centerBox.setMaxWidth(300);
		centerBox.getChildren().addAll(TITLE, nameEnter, submitButton);

		root.setTop(statusLabel);
		BorderPane.setAlignment(statusLabel, Pos.CENTER);
		root.setCenter(centerBox);
		Scene scene = new Scene(root, 300, 300, Color.LAVENDER);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public void start_gameStage(String name, Stage primaryStage) {
		this.primstage = primaryStage;
		BorderPane root = new BorderPane();

		String musicFile = "BackgroundMusic.mp3";
		try {
			Media sound = new Media(GameGUI.class.getResource(musicFile).toExternalForm());
			mediaPlayer = new MediaPlayer(sound);
			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayer.setVolume(0.2);
			mediaPlayer.play();
		} catch (Exception e) {
			System.out.println("Error loading music: " + e.getMessage());
		}

		homeZone1 = new HBox(10);
		setUIComponents(homeZone1, 684, 536, 64, 16, Pos.TOP_CENTER);
		homeZone2 = new VBox(10);
		setUIComponents(homeZone2, 464, 284, 16, 64, Pos.CENTER_RIGHT);
		homeZone3 = new HBox(10);
		setUIComponents(homeZone3, 684, 64, 64, 16, Pos.BOTTOM_CENTER);
		homeZone4 = new VBox(10);
		setUIComponents(homeZone4, 936, 284, 16, 64, Pos.CENTER_LEFT);

		homeZone1.setMaxSize(40, 40); 
		homeZone2.setMaxSize(40, 40); 
		homeZone3.setMaxSize(40, 40); 
		homeZone4.setMaxSize(40, 40); 

		safeZone1 = new VBox(10);
		setUIComponents(safeZone1, 740, 436, 16, 64, Pos.TOP_CENTER);
		safeZone2 = new HBox(10);
		setUIComponents(safeZone2, 516, 340, 64, 16, Pos.CENTER_RIGHT);
		safeZone3 = new VBox(10);
		setUIComponents(safeZone3, 660, 116, 16, 64, Pos.BOTTOM_CENTER);
		safeZone4 = new HBox(10);
		setUIComponents(safeZone4, 836, 260, 64, 16, Pos.CENTER_LEFT);

		safeZone1.setMaxSize(40, 40); 
		safeZone2.setMaxSize(40, 40); 
		safeZone3.setMaxSize(40, 40); 
		safeZone4.setMaxSize(40, 40); 

		bottomPanel = createPlayerPanel_Horizontal(name, Pos.BOTTOM_CENTER);
		leftPanel = createPlayerPanel_vertical("CPU 1", Pos.CENTER_LEFT);
		topPanel = createPlayerPanel_Horizontal("CPU 2", Pos.TOP_CENTER);
		rightPanel = createPlayerPanel_vertical("CPU 3", Pos.CENTER_RIGHT);
		bottomPanel.setTranslateY(-10);

		Sc_P1 = new Label((game.getPlayers().get(1).getSelectedCard()== null)? "No cards selected" :(""+game.getPlayers().get(1).getSelectedCard().getName()));
		Sc_P2 = new Label((game.getPlayers().get(2).getSelectedCard()== null)? "No cards selected" :(""+game.getPlayers().get(2).getSelectedCard().getName()));
		Sc_P3 = new Label((game.getPlayers().get(3).getSelectedCard()== null)? "No cards selected" :(""+game.getPlayers().get(3).getSelectedCard().getName()));

		Sc_P1.setMinWidth(180);
		Sc_P2.setMinWidth(180);
		Sc_P3.setMinWidth(180);

		BorderPane.setAlignment(Sc_P1,Pos.CENTER);
		BorderPane.setAlignment(Sc_P2,Pos.CENTER);
		BorderPane.setAlignment(Sc_P3,Pos.CENTER);

		leftPanel.getChildren().add(Sc_P1);
		topPanel.getChildren().add(Sc_P2);
		rightPanel.getChildren().add(Sc_P3);

		Label Label0 = new Label(""+game.getPlayers().get(0).getColour());
		Label Label1 = new Label(""+game.getPlayers().get(1).getColour());
		Label Label2 = new Label(""+game.getPlayers().get(2).getColour());
		Label Label3 = new Label(""+game.getPlayers().get(3).getColour());

		bottomPanel.getChildren().add(Label0);
		leftPanel.getChildren().add(Label1);
		topPanel.getChildren().add(Label2);
		rightPanel.getChildren().add(Label3);

		BorderPane.setAlignment(Label0,Pos.CENTER);
		BorderPane.setAlignment(Label1,Pos.CENTER);
		BorderPane.setAlignment(Label2,Pos.CENTER);
		BorderPane.setAlignment(Label3,Pos.CENTER_LEFT);


		track1 = new HBox(10);
		setUIComponents(track1, 300, 200, 400, 16, Pos.CENTER);
		track2 = new VBox(10);
		setUIComponents(track2, 700, 300, 16, 400, Pos.CENTER);
		track3 = new HBox(10);
		setUIComponents(track3, 300, 700, 400, 16, Pos.CENTER);
		track4 = new VBox(10);
		setUIComponents(track4, 300, 200, 16, 400, Pos.CENTER);


		BorderPane trackGrid = new BorderPane();
		trackGrid.setStyle("-fx-background-color: #ffebee; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: brown; -fx-border-width: 1px; -fx-padding: 10px;");
		track1.setMaxSize(400, 16); 
		track2.setMaxSize(16, 400); 
		track3.setMaxSize(400, 16); 
		track4.setMaxSize(16, 400);  

		trackGrid.setMaxSize(300, 300); 

		BorderPane.setAlignment(track1, Pos.CENTER); 
		BorderPane.setAlignment(track2, Pos.CENTER); 
		BorderPane.setAlignment(track3, Pos.CENTER); 
		BorderPane.setAlignment(track4, Pos.CENTER); 
		BorderPane.setAlignment(createFirepitArea(), Pos.CENTER); 

		Label trackLabel = new Label("TRACK AREA");
		trackLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

		currentplayerlabel = new Label();
		setUIComponents(currentplayerlabel, 50, 50, 200, 100,Pos.CENTER);

		nextplayerlabel = new Label();
		setUIComponents(nextplayerlabel, 50, 100, 200, 100,Pos.CENTER);

		turnLabel = new Label();
		setUIComponents(turnLabel, 50, 100, 200, 100,Pos.CENTER);

		trapCellLabel = new Label("No trap cell events");
		setUIComponents(trapCellLabel, 50, 130, 200, 100, Pos.CENTER);
		trapCellLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");

		humanplayer_hand = new HBox();
		setUIComponents(humanplayer_hand, 50, 150, 1000, 100, Pos.CENTER);
		humanplayer_hand.setMinSize(1000, 100);
		humanplayer_hand.setMaxSize(1000, 100);
		humanplayer_hand.setTranslateX(725);
		humanplayer_hand.setTranslateY(-15);//-100

		skipturn = new Button("Skip Turn");
		setUIComponents(skipturn, 50, 150, 200, 16, Pos.CENTER);
		skipturn.setOnAction(this);

		Button soundSettings = new Button("Sound Settings");

		soundSettings.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Stage alertStage = new Stage();
				alertStage.setTitle("Sound Settings");
				alertStage.initModality(Modality.APPLICATION_MODAL); // Make dialog modal
				alertStage.initOwner(primstage);

				Label label = new Label("Settings");
				Button closeButton = new Button("Continue Game");
				closeButton.setOnAction(event100 -> alertStage.close());

				HBox set = new HBox(20);
				Button playButton = new Button("Play Music");
				Button pauseButton = new Button("Pause Music");
				
				playButton.setStyle("-fx-font-size: 12;");
				pauseButton.setStyle("-fx-font-size: 12; ");

				playButton.setOnAction(e -> { if (mediaPlayer != null) mediaPlayer.play();});
				pauseButton.setOnAction(e -> { if (mediaPlayer != null) mediaPlayer.pause();});

				set.getChildren().addAll(playButton,pauseButton);
				set.setAlignment(Pos.CENTER);
				BorderPane pane = new BorderPane();
				pane.setTop(label);
				pane.setBottom(closeButton);
				pane.setCenter(set);

				BorderPane.setAlignment(label, Pos.CENTER);
				BorderPane.setAlignment(closeButton, Pos.CENTER);
				BorderPane.setAlignment(set, Pos.CENTER);

				Scene scene = new Scene(pane, 250, 100);
				alertStage.setScene(scene);
				alertStage.show();
			}
		});

		CardsInfo = new Button("Game Details");
		setUIComponents(CardsInfo, 50, 150, 200, 16, Pos.CENTER);
		CardsInfo.setOnAction(this);

		deselectAll = new Button("Clear Selection");
		setUIComponents(deselectAll, 50, 150, 200, 16, Pos.CENTER);
		deselectAll.setOnAction(this);

		HBox Right = new HBox(10);
		VBox ClrANDLabels = new VBox(10);
		BorderPane cd = createDeckArea() ;
		ClrANDLabels.getChildren().addAll(turnLabel, nextplayerlabel,currentplayerlabel,trapCellLabel,skipturn,deselectAll,CardsInfo,cd,soundSettings);
		Right.getChildren().addAll(ClrANDLabels, rightPanel);

		VBox top = new VBox(10);
		top.getChildren().addAll(trackLabel, track3);

		VBox bottom = new VBox();
		bottom.getChildren().addAll(humanplayer_hand, bottomPanel);

		bottomPanel.getChildren().addAll(homeZone1);
		rightPanel.getChildren().addAll(homeZone4);
		topPanel.getChildren().addAll(homeZone3);
		leftPanel.getChildren().addAll(homeZone2);

		trackGrid.setBottom(track1); 
		trackGrid.setTop(top);    
		trackGrid.setRight(track4);
		trackGrid.setLeft(track2);   

		trackGrid.setCenter(createFirepitArea());
		root.setBottom(bottom);
		root.setRight(Right);
		root.setTop(topPanel);
		root.setLeft(leftPanel);
		root.setCenter(trackGrid); 
		BorderPane.setMargin(bottom, new Insets(10));
		BorderPane.setMargin(Right, new Insets(10));
		BorderPane.setMargin(topPanel, new Insets(10));
		BorderPane.setMargin(leftPanel, new Insets(10));
		BorderPane.setMargin(trackGrid, new Insets(10));
		gameUpdate();

		Scene scene = new Scene(root, 1200, 800);

		scene.setOnKeyPressed(event -> {
			if (event.getCode() == KeyCode.M) {
				Player human_player = game.getPlayers().get(0);
				if (game.getActivePlayerColour() == human_player.getColour()) {
					Card targetCard = null;
					for (Card card : human_player.getHand()) {
						if (card.getName().contains("King") || card.getName().contains("Ace")) {
							targetCard = card;
							break;
						}
					}
					if (targetCard != null) {
						try {
							human_player.selectCard(targetCard);
							processTurn.run();
							turn = (turn + 1) % 4;

							// Process CPU turns with delays
							PauseTransition cpuDelay = new PauseTransition(Duration.seconds(2));
							cpuDelay.setOnFinished(e -> {
								// CPU 1
								processTurn.run();
								turn = (turn + 1) % 4;
								PauseTransition cpuDelay2 = new PauseTransition(Duration.seconds(2));
								cpuDelay2.setOnFinished(e2 -> {
									// CPU 2
									processTurn.run();
									turn = (turn + 1) % 4;
									PauseTransition cpuDelay3 = new PauseTransition(Duration.seconds(2));
									cpuDelay3.setOnFinished(e3 -> {
										// CPU 3
										processTurn.run();
										turn = (turn + 1) % 4;
										gameUpdate();
									});
									cpuDelay3.play();
								});
								cpuDelay2.play();
							});
							cpuDelay.play();

						} catch (Exception e) {
							displayAlert("Error", "Failed to select card: " + e.getMessage());
						}
					} else {
						displayAlert("Invalid Action", "No King or Ace in hand!");
					}
				} else {
					displayAlert("Invalid Action", "Can only field marbles on your turn!");
				}
			}
		});

		primaryStage.setScene(scene);
		primaryStage.setX(0);
		primaryStage.setY(0);
		primaryStage.setTitle("Jackaroo Game");
		primaryStage.setFullScreen(true);
		primaryStage.show();

	}

	public Image getImageName(Marble m) {
		if (m == null)
			return EMPTY;
		else if (m.getColour() == Colour.RED)
			return RED;
		else if (m.getColour() == Colour.BLUE)
			return BLUE;
		else if (m.getColour() == Colour.GREEN)
			return GREEN;
		else
			return YELLOW;
	}
	private Image getCardImage(Card card) {
		try {
			if (card instanceof Standard) {
				Standard standardCard = (Standard) card;
				int rank = standardCard.getRank();
				Suit suit = standardCard.getSuit();

				String rankStr;
				switch (rank) {
				case 1:
					rankStr = "1"; // Ace
					break;
				case 11:
					rankStr = "Jack";
					break;
				case 12:
					rankStr = "Queen";
					break;
				case 13:
					rankStr = "King";
					break;
				default:
					rankStr = String.valueOf(rank); 
					break;
				}

				switch (suit) {
				case CLUB:
					switch (rankStr) {
					case "1": return Aceclub;
					case "2": return twoclub;
					case "3": return threeclub;
					case "4": return fourclub;
					case "5": return fiveclub;
					case "6": return sixclub;
					case "7": return sevenclub;
					case "8": return eightclub;
					case "9": return nineclub;
					case "10": return tenclub;
					case "Jack": return jackclub;
					case "Queen": return queenclub;
					case "King": return kingclub;
					}
					break;
				case DIAMOND:
					switch (rankStr) {
					case "1": return AceDiamond;
					case "2": return twoDiamond;
					case "3": return threeDiamond;
					case "4": return fourDiamond;
					case "5": return fiveDiamond;
					case "6": return sixDiamond;
					case "7": return sevenDiamond;
					case "8": return eightDiamond;
					case "9": return nineDiamond;
					case "10": return tenDiamond;
					case "Jack": return jackDiamond;
					case "Queen": return queenDiamond;
					case "King": return kingDiamond;
					}
					break;
				case HEART:
					switch (rankStr) {
					case "1": return Aceheart;
					case "2": return twoheart;
					case "3": return threeheart;
					case "4": return fourheart;
					case "5": return fiveheart;
					case "6": return sixheart;
					case "7": return sevenheart;
					case "8": return eightheart;
					case "9": return nineheart;
					case "10": return tenheart;
					case "Jack": return jackheart;
					case "Queen": return queenheart;
					case "King": return kingheart;
					}
					break;
				case SPADE:
					switch (rankStr) {
					case "1": return Acespade;
					case "2": return twospade;
					case "3": return threespade;
					case "4": return fourspade;
					case "5": return fivespade;
					case "6": return sixspade;
					case "7": return sevenspade;
					case "8": return eightspade;
					case "9": return ninespade;
					case "10": return tenspade;
					case "Jack": return jackspade;
					case "Queen": return queenspade;
					case "King": return kingspade;
					}
					break;
				default:
					System.err.println("Unknown suit: " + suit + " for card: " + card.getName());
					return null;
				}
			} else {
				String cardName = card.getName();
				if (cardName.equals("MarbleBurner")){
					return MarbleBurner; 
				}
				if (cardName.equals("MarbleSaver")) {
					return MarbleSaver;
				}
				System.err.println("Unknown non-standard card: " + cardName);
				return null;
			}
		} catch (Exception e) {
			System.err.println("Error getting image for card: " + card.getName() + " - " + e.getMessage());
			return null;
		}
		return null;
	}
	public void trackupdate() {
		if (trackButtons == null || trackButtons.isEmpty()) {
			trackButtons = new ArrayList<>();
			for (int i = 0; i < 100; i++) {
				Button b = new Button();
				b.setMinSize(16, 16);
				b.setMaxSize(16, 16);
				b.setPrefSize(16, 16);
				b.setOnAction(this);
				trackButtons.add(b);
			}
		}
		track1.getChildren().clear();
		track2.getChildren().clear();
		track3.getChildren().clear();
		track4.getChildren().clear();

		for (int i = 12; i >= -12; i--) {
			int index = (i + 100) % 100;
			Cell c = game.getBoard().getTrack().get(index);
			try {
				ImageView image = new ImageView (getImageName(c.getMarble()));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				trackButtons.get(index).setGraphic(image);
				track1.getChildren().add(trackButtons.get(index));
			} catch (Exception e) {
				System.err.println("Failed to load image for track1, index " + index + e.getMessage());
				trackButtons.get(index).setGraphic(null);
				track1.getChildren().add(trackButtons.get(index));
			}
		}

		for (int i = 37; i >= 13; i--) {
			Cell c = game.getBoard().getTrack().get(i);

			try {
				ImageView image = new ImageView (getImageName(c.getMarble()));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				trackButtons.get(i).setGraphic(image);
				track2.getChildren().add(trackButtons.get(i));
			} catch (Exception e) {
				System.err.println("Failed to load image for track2, index " + i + e.getMessage());
				trackButtons.get(i).setGraphic(null);
				track2.getChildren().add(trackButtons.get(i));
			}
		}

		for (int i = 38; i <= 62; i++) {
			Cell c = game.getBoard().getTrack().get(i);

			try {
				ImageView image = new ImageView (getImageName(c.getMarble()));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				trackButtons.get(i).setGraphic(image);
				track3.getChildren().add(trackButtons.get(i));
			} catch (Exception e) {
				System.err.println("Failed to load image for track3, index " + i + e.getMessage());
				trackButtons.get(i).setGraphic(null);
				track3.getChildren().add(trackButtons.get(i));
			}
		}

		for (int i = 63; i <= 87; i++) {
			Cell c = game.getBoard().getTrack().get(i);

			try {
				ImageView image = new ImageView (getImageName(c.getMarble()));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				trackButtons.get(i).setGraphic(image);
				track4.getChildren().add(trackButtons.get(i));
			} catch (Exception e) {
				System.err.println("Failed to load image for track4, index " + i + e.getMessage());
				trackButtons.get(i).setGraphic(null);
				track4.getChildren().add(trackButtons.get(i));
			}
		}
		checkTrapCellEvent();
	}
	public void homezoneupdate() {
		homeZone1.getChildren().clear();
		homeZone2.getChildren().clear();
		homeZone3.getChildren().clear();
		homeZone4.getChildren().clear();

		for (int i = 0; i < game.getPlayers().get(0).getMarbles().size(); i++) {
			Marble marble = game.getPlayers().get(0).getMarbles().get(i);
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16); 
				image.setFitHeight(16); 
				image.setPreserveRatio(true); 
				homeZone1.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for homeZone1, index " + i + e.getMessage());
			}
		}

		for (int i = 0; i < game.getPlayers().get(1).getMarbles().size(); i++) {
			Marble marble = game.getPlayers().get(1).getMarbles().get(i);
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				homeZone2.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for homeZone2, index " + i + e.getMessage());
			}
		}

		for (int i = 0; i < game.getPlayers().get(2).getMarbles().size(); i++) {
			Marble marble = game.getPlayers().get(2).getMarbles().get(i);

			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				homeZone3.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for homeZone3, index " + i + e.getMessage());
			}
		}

		for (int i = 0; i < game.getPlayers().get(3).getMarbles().size(); i++) {
			Marble marble = game.getPlayers().get(3).getMarbles().get(i);
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				homeZone4.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for homeZone4, index " + i  + e.getMessage());
			}
		}
	}
	public void safezoneupdate() {
		safeZone1.getChildren().clear();
		safeZone2.getChildren().clear();
		safeZone3.getChildren().clear();
		safeZone4.getChildren().clear();

		for (int i = 0; i < 4; i++) {
			Marble marble = game.getBoard().getSafeZones().get(0).getCells().get(i).getMarble();
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				safeZone1.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for safeZone1, index " + i + e.getMessage());
			}
		}

		for (int i = 0; i < 4; i++) {
			Marble marble = game.getBoard().getSafeZones().get(1).getCells().get(i).getMarble();
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				safeZone2.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for safeZone2, index " + i + e.getMessage());
			}
		}

		for (int i = 0; i < 4; i++) {
			Marble marble = game.getBoard().getSafeZones().get(2).getCells().get(i).getMarble();
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				safeZone3.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for safeZone3, index " + i + e.getMessage());
			}
		}

		for (int i = 0; i < 4; i++) {
			Marble marble = game.getBoard().getSafeZones().get(3).getCells().get(i).getMarble();
			try {
				ImageView image = new ImageView (getImageName(marble));
				image.setFitWidth(16);
				image.setFitHeight(16);
				image.setPreserveRatio(true);
				safeZone4.getChildren().add(image);
			} catch (Exception e) {
				System.err.println("Failed to load image for safeZone4, index " + i + e.getMessage());
			}
		}
	}
	public void addDelay() {
		PauseTransition delay = new PauseTransition(Duration.seconds(20)); // 2-second delay
		delay.setOnFinished(event -> {
			System.out.println("Delay finished!");
		});
		delay.play();
	}
	public void gameUpdate() {
		updateCpu_Cards();
		Player human_player= game.getPlayers().get(0);

		if ((!game.canPlayTurn())&&(game.getActivePlayerColour() == human_player.getColour())) {
			displayAlert("Invalid Action", "YOUR TURN WAS SKIPPED");
			game.endPlayerTurn();
			turn = (turn + 1) % 4;

			PauseTransition cpuDelay = new PauseTransition(Duration.seconds(2));
			cpuDelay.setOnFinished(e -> {
				// CPU 1
				processTurn.run();
				turn = (turn + 1) % 4;
				PauseTransition cpuDelay2 = new PauseTransition(Duration.seconds(2));
				cpuDelay2.setOnFinished(e2 -> {
					// CPU 2
					processTurn.run();
					turn = (turn + 1) % 4;
					PauseTransition cpuDelay3 = new PauseTransition(Duration.seconds(2));
					cpuDelay3.setOnFinished(e3 -> {
						// CPU 3
						processTurn.run();
						turn = (turn + 1) % 4;
						gameUpdate();
					});
					cpuDelay3.play();
				});
				cpuDelay2.play();
			});
			cpuDelay.play();
		}

		trackupdate();
		homezoneupdate();
		safezoneupdate();


		for (int i = 0; i < game.getPlayers().size(); i++) {
			if(game.getActivePlayerColour() == game.getPlayers().get(i).getColour())
				currentplayerlabel.setText("Current player: "+game.getPlayers().get(i).getName());

			String bgColor = getColourStyle(game.getActivePlayerColour());
			currentplayerlabel.setStyle("-fx-background-color: " + bgColor + "; -fx-text-fill: #FFFFFF;  -fx-font-size: 14; -fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px; -fx-background-radius: 5;");
		}

		for (int i = 0; i < game.getPlayers().size(); i++) {
			if(game.getNextPlayerColour() == game.getPlayers().get(i).getColour()){
				nextplayerlabel.setText("Next player: "+ game.getPlayers().get(i).getName());
			}	
		}

		turnLabel.setText("Turn #" + (turn+1));

		if (game.getFirePit().size() > 0) {
			int lastIndex = game.getFirePit().size() - 1;
			if (game.getFirePit().get(lastIndex) != null) {
				Image cardImage = getCardImage(game.getFirePit().get(lastIndex));
				if (cardImage != null) {
					try {
						ImageView imageView = new ImageView(cardImage);
						imageView.setFitWidth(80);  // Adjust size to fit firepit area
						imageView.setFitHeight(100);
						imageView.setPreserveRatio(true);
						firePitLabel.setText("");  // Clear the text
						firePitLabel.setGraphic(imageView);  // Set the card image
					} catch (Exception e) {
						System.err.println("Failed to set firepit card image: " + e.getMessage());
						firePitLabel.setText(game.getFirePit().get(lastIndex).getName());  // Fallback to text
						firePitLabel.setGraphic(null);
					}
				} else {
					firePitLabel.setText(game.getFirePit().get(lastIndex).getName());  // Fallback to text if image is null
					firePitLabel.setGraphic(null);
				}
			} else {
				firePitLabel.setText("FIREPIT");
				firePitLabel.setGraphic(null);
			}
		} else {
			firePitLabel.setText("FIREPIT");
			firePitLabel.setGraphic(null);
		}

		humanplayer_hand.getChildren().clear();
		cardsButtons = new ArrayList<Button>();

		for (int i = 0; i < game.getPlayers().get(0).getHand().size(); i++) {
			Card c = game.getPlayers().get(0).getHand().get(i);
			String cardInfo = "";
			if (c instanceof Standard) {
				Standard x = (Standard) c;
				cardInfo = x.getName() + " / " + x.getRank();
			} else {
				cardInfo = c.getName();
			}
			Button b = new Button(cardInfo);
			b.setStyle("-fx-font-size: 10pt; -fx-padding: 5px; -fx-alignment: center; -fx-border-color: black; -fx-border-width: 1px;");
			b.setPrefSize(80, 100);
			b.setMinSize(80, 100);
			b.setMaxSize(80, 100);

			Image cardImage = getCardImage(c);
			if (cardImage != null) {
				try {
					ImageView imageView = new ImageView(cardImage);
					imageView.setFitWidth(70);
					imageView.setFitHeight(100);
					imageView.setPreserveRatio(true);
					b.setGraphic(imageView);
					b.setContentDisplay(ContentDisplay.TOP);
				} catch (Exception e) {
					System.err.println("Failed to set card image for: " + cardInfo + " - " + e.getMessage());
				}
			} else {
				System.err.println("No image found for card: " + cardInfo);
			}

			Tooltip tooltip = new Tooltip(getCardActionDescription(c));
			tooltip.setStyle("-fx-font-size: 12pt; -fx-background-color: #f0f0f0; -fx-text-fill: black; -fx-padding: 10px; -fx-background-radius: 5px;");
			b.setTooltip(tooltip);

			b.setOnAction(this);
			cardsButtons.add(b);
			humanplayer_hand.getChildren().add(b);
		}

//						Colour winner = Colour.BLUE;
		Colour winner = game.checkWin();
		if (winner != null) {

			if (mediaPlayer != null) {
				mediaPlayer.stop();
			}
			String musicFile = "Endgame.mp3";
			try {
				Media sound = new Media(GameGUI.class.getResource(musicFile).toExternalForm());
				Endgame = new MediaPlayer(sound);
				Endgame.setCycleCount(MediaPlayer.INDEFINITE);
				Endgame.setVolume(0.2);
				Endgame.play();
			} catch (Exception e) {
				System.out.println("Error loading music: " + e.getMessage());
			}

			Stage alertStage = new Stage();
			Label label= new Label();
			alertStage.initModality(Modality.APPLICATION_MODAL); // Make dialog modal
			alertStage.initOwner(primstage);
			alertStage.setTitle("We Have a Winner!");

			if(winner == game.getPlayers().get(0).getColour()){

				label = new Label("CONGRATS YOU WON THE GAME");
			}else{
				alertStage.setTitle("Game Over Player with colour " + winner + " has won the game!");
			}

			Button closeButton = new Button("BYE BYE");

			closeButton.setOnAction(event -> {
				alertStage.close();
				primstage.close();
				if (Endgame != null) {
					Endgame.stop();
				}
			});
			BorderPane pane = new BorderPane();
			pane.setTop(label);
			pane.setCenter(closeButton);

			Scene scene2 = new Scene(pane, 500, 100);
			alertStage.setScene(scene2);
			alertStage.show();
			return;

		}
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(e -> {
		});
		pause.play();
	}
	private String getColourStyle(Colour colour) {
		switch (colour) {
		case RED: return "#d32f2f";
		case YELLOW: return "#fbc02d";
		case GREEN: return "#388e3c";
		case BLUE: return "#0288d1";
		default: return "#000000";
		}
	}
	private String getCardActionDescription(Card card) {
		String cardName = card.getName().toLowerCase();
		if (card instanceof Standard) {
			Standard standardCard = (Standard) card;
			int rank = standardCard.getRank();
			switch (rank) {
			case 1: // Ace
				return "Fields a marble from the Home Zone adhering to fielding rules OR acts as a standard card moving one of your own marbles 1 step forward.";
			case 2: case 3: case 6: case 8: case 9:
				return "Moves one of your own marbles " + rank + " steps forward adhering to movement rules.";
			case 4:
				return "Moves one of your own marbles 4 steps BACKWARDS adhering to movement rules.";
			case 5:
				return "Moves any marble on track 5 steps adhering to movement rules.";
			case 7:
				return "Moves two of your own marbles a total of 7 forward steps adhering to movement rules according to a chosen split distance from 1 to 6 OR acts as a standard card.";
			case 10:
				return "A random card is discarded from the next player's hand and skips their turn adhering to discarding/skipping rules OR acts as a standard card.";
			case 11: // Jack
				return "Swaps one of your own marbles with another adhering to swapping rules OR acts as a standard card.";
			case 12: // Queen
				return "A random card is discarded from a random player's hand and skips their turn adhering to discarding/skipping rules OR acts as a standard card.";
			case 13: // King
				return "Fields a marble from the Home Zone adhering to fielding rules OR moves one of your own marbles 13 steps forward destroying all marbles in its path, bypassing self-blocking, path blockage, and Safe Zone entry rules.";
			default:
				return "Unknown card action.";
			}
		} else {
			if (cardName.contains("burner")) {
				return "Sends an opponent's marble back to its player's Home Zone adhering to burning rules.";
			} else if (cardName.contains("saver")) {
				return "Sends one of your own marbles to one of your own random empty Safe Zone cell adhering to saving rules.";
			}
		}
		return "Unknown card action.";
	}

	private void setUIComponents(HBox c, int x, int y, int w, int h, Pos alignment) {
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setMaxSize(w, h);
		c.setAlignment(alignment);
	}
	private void setUIComponents(VBox c, int x, int y, int w, int h, Pos alignment) {
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setMaxSize(w, h);
		c.setAlignment(alignment);
	}
	private void setUIComponents(Button c, int x, int y, int w, int h, Pos alignment) {
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setMaxSize(w, h);
		c.setAlignment(alignment);
	}
	private void setUIComponents(Label c, int x, int y, int w, int h, Pos alignment) {
		c.setLayoutX(x);
		c.setLayoutY(y);
		c.setMaxSize(w, h);
		c.setAlignment(alignment);
		c.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");
	}
	private VBox createPlayerPanel_vertical(String playerName, Pos alignment) {
		VBox panel = new VBox(10);
		panel.setAlignment(alignment);
		panel.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");

		Label nameLabel = new Label(playerName);

		nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");
		updateCards(panel);
		panel.getChildren().addAll(nameLabel);
		return panel;
	}
	private void updateCards(VBox panel) {
		for (int i = 0; i < 4; i++) {
			Label cardPlaceholder = new Label("Card " + (i+1));
			cardPlaceholder.setPrefSize(80, 100);
			cardPlaceholder.setMinSize(80, 100);
			cardPlaceholder.setMaxSize(80, 100);
			cardPlaceholder.setStyle("-fx-background-color: gray;-fx-border-color:black; -fx-padding: 5px;");
			panel.getChildren().add(cardPlaceholder);
		}
	}
	private void updateCards(HBox panel) {
		for (int i = 0; i < 4; i++) {
			Label cardPlaceholder = new Label("Card " + (i+1));
			cardPlaceholder.setPrefSize(80, 100);
			cardPlaceholder.setMinSize(80, 100);
			cardPlaceholder.setMaxSize(80, 100);
			cardPlaceholder.setStyle("-fx-background-color: gray;-fx-border-color:black; -fx-padding: 5px;");
			panel.getChildren().add(cardPlaceholder);
		}
	}
	private void updateCpu_Cards() {

		// Update cards for all CPU panels
		updateCards(leftPanel, game.getPlayers().get(1).getHand().size());  // CPU 1 (vertical)
		Sc_P1.setText(lastPlayedCards[1] == null ? "Last selected Card: " :("Last selected Card: " + lastPlayedCards[1]));

		updateCards(topPanel, game.getPlayers().get(2).getHand().size());   // CPU 2 (horizontal)
		Sc_P2.setText(lastPlayedCards[2] == null ? "Last selected Card: " : ("Last selected Card: " + lastPlayedCards[2]));

		updateCards(rightPanel, game.getPlayers().get(3).getHand().size()); // CPU 3 (vertical)
		Sc_P3.setText(lastPlayedCards[3] == null ? "Last selected Card: " : ("Last selected Card: " + lastPlayedCards[3]));

	}

	private void updateCards(VBox panel, int max) {
		panel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().startsWith("Card "));
		for (int i = 0; i < max; i++) {
	        Label cardPlaceholder = new Label("Card " + (i + 1));
	        cardPlaceholder.setPrefSize(100, 80);
	        cardPlaceholder.setMinSize(100, 80);
	        cardPlaceholder.setMaxSize(100, 80);
	        cardPlaceholder.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-padding: 5px;");
	        cardPlaceholder.setAlignment(Pos.CENTER);
	        try {
	            ImageView iconView = new ImageView(Back2);
	            iconView.setFitWidth(100);
	            iconView.setFitHeight(80);
	            iconView.setPreserveRatio(false);
	            cardPlaceholder.setGraphic(iconView);
	            cardPlaceholder.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Text is present but not visible
	        } catch (Exception e) {
	            System.err.println("Failed to load card back image for index " + i + ": " + e.getMessage());
	            cardPlaceholder.setGraphic(null);
	        }
	        panel.getChildren().add(cardPlaceholder);
	    }
	}

	private void updateCards(HBox panel, int max) {
		panel.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().startsWith("Card "));
		for (int i = 0; i < max; i++) {
	        Label cardPlaceholder = new Label("Card " + (i + 1));
	        cardPlaceholder.setPrefSize(100, 80);
	        cardPlaceholder.setMinSize(100, 80);
	        cardPlaceholder.setMaxSize(100, 80);
	        cardPlaceholder.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-padding: 5px;");
	        cardPlaceholder.setAlignment(Pos.CENTER);
	        try {
	            ImageView iconView = new ImageView(Back2);
	            iconView.setFitWidth(100);
	            iconView.setFitHeight(80);
	            iconView.setPreserveRatio(false);
	            cardPlaceholder.setGraphic(iconView);
	            cardPlaceholder.setContentDisplay(ContentDisplay.GRAPHIC_ONLY); // Text is present but not visible
	        } catch (Exception e) {
	            System.err.println("Failed to load card back image for index " + i + ": " + e.getMessage());
	            cardPlaceholder.setGraphic(null);
	        }
	        panel.getChildren().add(cardPlaceholder);
	    }
	}

	private HBox createPlayerPanel_Horizontal(String playerName, Pos alignment) {
		HBox panel = new HBox(10);
		panel.setAlignment(alignment);
		panel.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");

		Label nameLabel = new Label(playerName);
		nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14pt;");

		if(game.getPlayers().get(turn).getColour() != game.getActivePlayerColour())
			updateCards(panel);

		panel.getChildren().add(0, nameLabel);
		return panel;
	}
	ArrayList<Integer> currentTrapCellIndices;
	ArrayList <Integer> PreviousTrapCellIndices;

	private ArrayList <Integer>  findIdx(){
		ArrayList <Integer>  temp = new ArrayList<Integer>();
		for (int i = 0; i < game.getBoard().getTrack().size(); i++) {
			Cell cell = game.getBoard().getTrack().get(i);
			if (cell.isTrap()) {
				temp.add(i);
			}
		} return temp;
	}
	
	private void checkTrapCellEvent() {
		if(PreviousTrapCellIndices == null){
			PreviousTrapCellIndices  = findIdx();
		}

		currentTrapCellIndices = findIdx();
		PreviousTrapCellIndices.sort(null);
		currentTrapCellIndices.sort(null);
		if(PreviousTrapCellIndices.isEmpty() ||currentTrapCellIndices.isEmpty() )
			return;
		for (int i = 0; i < PreviousTrapCellIndices.size() ; i++) {
			if(PreviousTrapCellIndices.get(i)!= currentTrapCellIndices.get(i)){

				trapCellLabel.setText("TRAPP TRIGGERED");
				trapCellLabel.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");
				PauseTransition pause = new PauseTransition(Duration.seconds(5));
				pause.setOnFinished(e -> {
					trapCellLabel.setText("No trap cell events");
					trapCellLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");
				});
				pause.play();
			}
		}
		PreviousTrapCellIndices = currentTrapCellIndices;
	}
	
	private BorderPane createFirepitArea() {
		BorderPane centerGrid = new BorderPane();
		centerGrid.setStyle("-fx-background-color: brown; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-color: black; -fx-border-width: 1px; -fx-padding: 10px;");

		centerGrid.setBottom(safeZone1);
		safeZone1.setAlignment(Pos.BOTTOM_CENTER);

		centerGrid.setRight(safeZone4);
		safeZone2.setAlignment(Pos.CENTER);

		centerGrid.setTop(safeZone3);
		safeZone3.setAlignment(Pos.CENTER);

		centerGrid.setLeft(safeZone2);
		safeZone4.setAlignment(Pos.CENTER);

		centerGrid.setMaxSize(250,250);

		BorderPane.setAlignment(safeZone1, Pos.CENTER);
		BorderPane.setAlignment(safeZone2, Pos.CENTER);
		BorderPane.setAlignment(safeZone3, Pos.CENTER);
		BorderPane.setAlignment(safeZone4, Pos.CENTER);



		firepit = new VBox();
		firepit.setStyle("-fx-background-color: white; -fx-border-color: red; -fx-border-width: 1px;-fx-padding: 20; -fx-alignment: center;");
		firepit.setMaxSize(100, 100);

		firePitLabel = new Label("FIREPIT");
		firePitLabel.setFont(new Font("Arial", 24));
		setUIComponents(firePitLabel, 650, 285, 100, 30, Pos.CENTER);
		firePitLabel.setMinSize(200, 200);
		firepit.getChildren().add(firePitLabel);
		centerGrid.setCenter(firepit);

		return centerGrid;
	}
	private BorderPane createDeckArea() {
		BorderPane centerGrid = new BorderPane();
		centerGrid.setMaxSize(250,250);
		deck = new VBox();
		deck.setStyle("-fx-background-color: gray; -fx-border-color: black; -fx-border-width: 1px;-fx-padding: 20; -fx-alignment: center;");
		deck.setMaxSize(100, 100);

		decklabel = new Label("DECK");
		decklabel.setFont(new Font("Arial", 24));
		setUIComponents(decklabel, 650, 285, 100, 30, Pos.CENTER);
		decklabel.setMinSize(100,150);
		deck.getChildren().add(decklabel);
		centerGrid.setCenter(deck);

		return centerGrid;
	}
	public void testImageFormats() {
		String[] imagePaths = {"/sources/empty.png", "/sources/red.png", "/sources/blue.png", "/sources/green.png", "/sources/yellow.png"};
		for (String path : imagePaths) {
			try {
				InputStream is = getClass().getResourceAsStream(path);
				if (is == null) {
					System.err.println("Resource not found: " + path);
					continue;
				}
				byte[] header = new byte[8];
				is.read(header);
				is.close();
				boolean isPng = header[0] == (byte) 0x89 && header[1] == 'P' && header[2] == 'N' && header[3] == 'G';
				System.out.println(path + " is PNG: " + isPng);
			} catch (Exception e) {
				System.err.println("Error checking format for " + path + ": " + e.getMessage());
			}
		}
	}
	@SuppressWarnings("unused")
	public void testImageLoading() {
		String[] imagePaths = {"/sources/empty.png", "/sources/red.png", "/sources/blue.png", "/sources/green.png", "/sources/yellow.png"};
		for (String path : imagePaths) {
			try {
				Image image = new Image(getClass().getResourceAsStream(path));
				System.out.println("Successfully loaded: " + path);
			} catch (Exception e) {
				System.err.println("Failed to load: " + path + " - " + e.getMessage());
			}
		}
	}
	public void checkImageSizes() {
		String[] imagePaths = {"/sources/empty.png", "/sources/red.png", "/sources/blue.png", "/sources/green.png", "/sources/yellow.png"};
		for (String path : imagePaths) {
			try {
				Image image = new Image(getClass().getResourceAsStream(path));
				System.out.println(path + ": " + image.getWidth() + "x" + image.getHeight());
			} catch (Exception e) {
				System.err.println("Error checking size for " + path + ": " + e.getMessage());
			}
		}
	}
	@Override
	public void handle(ActionEvent event) {

		Player human_player = game.getPlayers().get(0);
		if (event.getSource() == CardsInfo) {
			Stage alertStage = new Stage();
			alertStage.setTitle("Cards Info");

			alertStage.initModality(Modality.APPLICATION_MODAL); // Make dialog modal
			alertStage.initOwner(primstage);

			Label label = new Label("game details");
			label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
			Button closeButton = new Button("Continue Game");
			closeButton.setOnAction(event2 -> alertStage.close());

			Image gameDetailsImage = new Image(GameGUI.class.getResourceAsStream("/sources/gamedetails.png"));
			ImageView imageView = new ImageView(gameDetailsImage);
			imageView.setFitWidth(400); 
			imageView.setPreserveRatio(true);

			BorderPane pane = new BorderPane();
			pane.setTop(label);
			pane.setCenter(imageView);
			pane.setBottom(closeButton); 

			BorderPane.setAlignment(label, Pos.CENTER);
			BorderPane.setAlignment(imageView, Pos.CENTER);
			BorderPane.setAlignment(closeButton, Pos.CENTER);

			closeButton.setTranslateY(-10); // -150 for less than 15" laptops !!!!!
			Scene scene = new Scene(pane, 800, 800); 
			alertStage.setScene(scene);
			alertStage.show();
		}

		if (game.getActivePlayerColour() != human_player.getColour()) {
			displayAlert("Invalid Action", "It's not your turn!");
			return;
		}

		if (event.getSource() == skipturn) {
			if (human_player.getSelectedCard() == null && human_player.getHand().size() != 0) {
				int r = (int) (Math.random() * human_player.getHand().size());
				try {
					human_player.selectCard(human_player.getHand().get(r));
				}catch (InvalidCardException e1) {

				}
			}
			game.endPlayerTurn();
			turn = (turn + 1) % 4;

			// Process CPU turns with delays
			PauseTransition cpuDelay = new PauseTransition(Duration.seconds(2));
			cpuDelay.setOnFinished(e -> {
				// CPU 1
				processTurn.run();
				turn = (turn + 1) % 4;
				PauseTransition cpuDelay2 = new PauseTransition(Duration.seconds(2));
				cpuDelay2.setOnFinished(e2 -> {
					// CPU 2
					processTurn.run();
					turn = (turn + 1) % 4;
					PauseTransition cpuDelay3 = new PauseTransition(Duration.seconds(2));
					cpuDelay3.setOnFinished(e3 -> {
						// CPU 3
						processTurn.run();
						turn = (turn + 1) % 4;
						gameUpdate();
					});
					cpuDelay3.play();
				});
				cpuDelay2.play();
			});
			cpuDelay.play();
		}

		//Deselectall button
		if(event.getSource() == deselectAll){
			game.deselectAll();
		}

		for (int i = 0; i < trackButtons.size(); i++) {
			if (event.getSource() == trackButtons.get(i)) {
				try {
					Marble marble = game.getBoard().getTrack().get(i).getMarble();
					human_player.selectMarble(marble);
				} catch (InvalidMarbleException e) {
					displayAlert("error", e.getMessage());
				}catch (Exception e) {
					displayAlert("error", e.getMessage());
				}
			}
		}

		for (int i = 0; i < cardsButtons.size(); i++) {
			if (event.getSource() == cardsButtons.get(i)) {
				Card c = human_player.getHand().get(i);
				try {
					human_player.selectCard(c);
					if (game.canPlayTurn()) {
						if(c instanceof Seven){

							try {
								Stage alertStage = new Stage();
								alertStage.setTitle("seven Card moves");

								Label label = new Label("Choose action");
								Button closeButton = new Button("Use as a Standard Card");
								closeButton.setOnAction(event1 -> alertStage.close());

								Button SetSplit_distance = new Button("SetSplit_distance");

								VBox pane = new VBox();
								HBox paneB = new HBox();
								paneB.getChildren().addAll(closeButton,SetSplit_distance);
								pane.getChildren().addAll(label,paneB);
								Scene scene = new Scene(pane, 500, 100);
								alertStage.setScene(scene);

								VBox pane2 = new VBox();

								TextField nameEnter = new TextField();
								nameEnter.setPromptText("Enter 1st marbles distance ");
								nameEnter.setStyle("-fx-prompt-text-fill: gray;");
								nameEnter.setMaxWidth(200);

								Label statusLabel = new Label("");
								statusLabel.setStyle("-fx-text-fill: red;");

								Button closeButton2 = new Button("Continue Game");

								closeButton2.setOnAction(new EventHandler<ActionEvent>() {

									@Override
									public void handle(ActionEvent event) {
										// TODO Auto-generated method stub
										String playerName = nameEnter.getText().trim();
										try {
											if (playerName.isEmpty()) {
												throw new IllegalArgumentException("Can't leave empty");
											}else{
												try {
													game.editSplitDistance(Integer.parseInt(playerName));
													alertStage.close();
												} catch (SplitOutOfRangeException e) {
													statusLabel.setText("Please enter a valid num.");
													statusLabel.setStyle("-fx-text-fill: red; " +
															"-fx-background-color: rgba(255, 255, 255, 0.8); " +
															"-fx-padding: 5px; " +
															"-fx-background-radius: 5px;");
												} 
											}
										} catch (IllegalArgumentException e) {
											statusLabel.setText("Please enter a valid num.");
											statusLabel.setStyle("-fx-text-fill: red; " +
													"-fx-background-color: rgba(255, 255, 255, 0.8); " +
													"-fx-padding: 5px; " +
													"-fx-background-radius: 5px;");
										}		
									}
								});

								pane2.getChildren().addAll(nameEnter,statusLabel,closeButton2);
								Scene scene2 = new Scene(pane2, 500, 100);
								SetSplit_distance.setOnAction(event1 -> alertStage.setScene(scene2));
								alertStage.showAndWait();
							} catch(Exception e) {
								e.printStackTrace();
							}

						}
						game.playPlayerTurn();
						if(c instanceof Queen || c instanceof Ten ){
							updateCpu_Cards();
						}
					}
					game.endPlayerTurn();
					turn = (turn + 1) % 4;

					// Process CPU turns with delays
					PauseTransition cpuDelay = new PauseTransition(Duration.seconds(2));
					cpuDelay.setOnFinished(e -> {
						// CPU 1
						processTurn.run();
						turn = (turn + 1) % 4;
						PauseTransition cpuDelay2 = new PauseTransition(Duration.seconds(2));
						cpuDelay2.setOnFinished(e2 -> {
							// CPU 2
							processTurn.run();
							turn = (turn + 1) % 4;
							PauseTransition cpuDelay3 = new PauseTransition(Duration.seconds(2));
							cpuDelay3.setOnFinished(e3 -> {
								// CPU 3
								processTurn.run();
								turn = (turn + 1) % 4;
								gameUpdate();
							});
							cpuDelay3.play();
						});
						cpuDelay2.play();
					});
					cpuDelay.play();


				}catch (Exception e) {
					displayAlert("error", e.getMessage());
				}

				break;
			}

		}
		gameUpdate();
	}

	private void displayAlert(String title, String message) {
		Stage alertStage = new Stage();
		alertStage.setTitle(title);
		alertStage.initModality(Modality.APPLICATION_MODAL); // Make dialog modal
		alertStage.initOwner(primstage);

		Label label = new Label(message);
		Button closeButton = new Button("Continue Game");
		closeButton.setOnAction(event -> alertStage.close());

		BorderPane pane = new BorderPane();
		pane.setTop(label);
		pane.setCenter(closeButton);

		Scene scene = new Scene(pane, 500, 100);
		alertStage.setScene(scene);
		alertStage.show();
	}
	public static void main(String[] args) {
		//		GameGUI c = new GameGUI();
		//		c.checkImageSizes();
		//		c.testImageFormats();

		launch(args);
	}
}