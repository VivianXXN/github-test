package com.core;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

public abstract class GameScreen extends Canvas implements GameEvent {
	protected enum GameState {
		GAME_MENU, GAME_START, GAME_CONTINUE, GAME_HELP, GAME_SET, GAME_EXIT, GAME_PAUSE, GAME_OVER, GAME_WIN
	};

	public List<GameObject> mObjects = new ArrayList<GameObject>();
	private Timeline timeline;
	private KeyFrame keyFrame;
	private int duration = 20;

	protected GameState mGameState = GameState.GAME_MENU;

	public GameScreen(double width, double height) {
		super(width, height);
		initTimeLine();
	}

	public void initEvents() {
		getParent().getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				onKeyPressed(event);
			}
		});

		getParent().getScene().setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				onKeyReleased(event);
			}
		});

		getParent().getScene().setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				onMouseMoved(event);
			}
		});
		
		getParent().getScene().setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				onMouseClicked(event);
			}
		});
	}

	public void onKeyPressed(KeyEvent event) {
		for (GameObject wObject : mObjects) {
			wObject.onKeyPressed(event);
		}
	}

	public void onKeyReleased(KeyEvent event) {
		for (GameObject wObject : mObjects) {
			wObject.onKeyReleased(event);
		}
	}

	public void onMouseMoved(MouseEvent event) {
		for (GameObject wObject : mObjects) {
			wObject.onMouseMoved(event);
		}
	}
	
	@Override
	public void onMouseClicked(MouseEvent event){
		for (GameObject wObject : mObjects) {
			wObject.onMouseClicked(event);
		}
	}

	/**
	 * add the object
	 * 
	 * @param baseObject
	 *            the object to be add
	 */
	public void addObject(GameObject baseObject) {
		this.mObjects.add(baseObject);
		baseObject.init();
	}

	/**
	 * remove the object
	 * 
	 * @param baseObject
	 *            the object to be remove
	 */
	public void removeObject(GameObject baseObject) {
		this.mObjects.remove(baseObject);
	}

	/**
	 * remove the object with the index
	 * 
	 * @param index
	 *            the index of the object
	 */
	public void removeObjectAtIndex(int index) {
		this.mObjects.remove(index);
	}
	/**
	 * init all the objects
	 */
	public void initObject() {
		for (int i = 0; i < mObjects.size(); i++) {
			GameObject wObject = mObjects.get(i);
			wObject.init();
		}
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * draw the objects
	 * 
	 * @param gc
	 */
	public void draw(GraphicsContext gc) {
		gc.setEffect(null);
		gc.clearRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < mObjects.size(); i++) {
			GameObject wObject = mObjects.get(i);
			if (wObject.isVisible()) {
				wObject.draw(gc);
			}
		}
	}

	/**
	 * update all the objects
	 */
	public void update() {
		for (int i = 0; i < mObjects.size(); i++) {
			GameObject wObject = mObjects.get(i);
			if (wObject.isUpdate()) {
				mObjects.get(i).update();
			}
		}
	}

	/**
	 * init the timeline
	 */
	private void initTimeLine() {
		timeline = new Timeline();
		timeline.setCycleCount(Timeline.INDEFINITE);
		keyFrame = new KeyFrame(Duration.millis(duration), new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				update();
				draw(getGraphicsContext2D());
			}
		});
		timeline.getKeyFrames().add(keyFrame);
	}

	/**
	 * start the update timeline
	 */
	public void start() {
		timeline.play();
	}

	/**
	 * pause the update timeline
	 */
	public void pause() {
		timeline.pause();
	}

	/**
	 * stop the update timeline
	 */
	public void stop() {
		timeline.stop();
	}

}
