package biz.zacneubert.raspbert.lineracers;

import android.hardware.SensorManager;
import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static org.andengine.extension.physics.box2d.util.constants.PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.IEntityComparator;
import org.andengine.entity.IEntityMatcher;
import org.andengine.entity.IEntityParameterCallable;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.IBackground;
import org.andengine.entity.shape.IAreaShape;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.shape.Shape;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.util.FPSLogger;
import org.andengine.entity.util.ScreenCapture;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.extension.physics.box2d.util.Vector2Pool;
import org.andengine.input.sensor.acceleration.AccelerationData;
import org.andengine.input.sensor.acceleration.IAccelerationListener;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.shader.ShaderProgram;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.opengl.vbo.IVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.IDisposable;
import org.andengine.util.adt.transformation.Transformation;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.IModifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import biz.zacneubert.raspbert.lineracers.Music.Music;

/**
 * Created by zacneubert on 3/21/16.
 */
public class GameplayActivity extends SimpleBaseGameActivity implements IOnSceneTouchListener,IAccelerationListener,IUpdateHandler {

    private static final int CAMERA_WIDTH = 720;
    private static final int CAMERA_HEIGHT = 480;

    private PhysicsWorld mPhysicsWorld;
    private Scene mScene;
    private TextureRegion mBoxFaceTextureRegion;
    private BitmapTextureAtlas mBitmapTextureAtlas;

    //private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1, 0.5f, 0.5f);
    private static final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(1,.5f,.1f);

    List<Body> boats = new ArrayList<>();

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mBitmapTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(), 512, 256, TextureOptions.BILINEAR);
        this.mBoxFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromResource(mBitmapTextureAtlas,this,R.drawable.tinyboat,0,0);
        this.mBoxFaceTextureRegion.set(0,0,26*4,8*4);

        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas);
        this.mBitmapTextureAtlas.load();
    }

    private final int rWidth = 60;
    private final int rHeight = 60;
    @Override
    protected Scene onCreateScene() {
        Music.BackgroundMusicChanged(R.raw.acid);
        this.mEngine.registerUpdateHandler(new FPSLogger());
        this.mEngine.registerUpdateHandler(this);

        this.mScene = new Scene();
        this.mScene.setBackground(new Background(0, 0, 150));
        this.mScene.setOnSceneTouchListener(this);

        this.mPhysicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_MOON), false);

        final VertexBufferObjectManager vertexBufferObjectManager = this.getVertexBufferObjectManager();
        final Rectangle ground = new Rectangle(0, CAMERA_HEIGHT - 20, CAMERA_WIDTH, 20, vertexBufferObjectManager);
        final Rectangle roof = new Rectangle(0, 0, CAMERA_WIDTH, 20, vertexBufferObjectManager);
        final Rectangle left = new Rectangle(0, 0, 20, CAMERA_HEIGHT, vertexBufferObjectManager);
        final Rectangle right = new Rectangle(CAMERA_WIDTH - 20, 0, 20, CAMERA_HEIGHT, vertexBufferObjectManager);

        final FixtureDef wallFixtureDef = PhysicsFactory.createFixtureDef(0, 0.5f, 0.5f);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, ground, BodyDef.BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, roof, BodyDef.BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, left, BodyDef.BodyType.StaticBody, wallFixtureDef);
        PhysicsFactory.createBoxBody(this.mPhysicsWorld, right, BodyDef.BodyType.StaticBody, wallFixtureDef);

        this.mScene.attachChild(ground);
        this.mScene.attachChild(roof);
        this.mScene.attachChild(left);
        this.mScene.attachChild(right);

        this.mScene.registerUpdateHandler(this.mPhysicsWorld);

        return this.mScene;
    }

    private Rectangle makeColoredRectangle(final float pX, final float pY, final float pRed, final float pGreen, final float pBlue) {
        final Rectangle coloredRect = new Rectangle(pX, pY, rWidth, rHeight, this.getVertexBufferObjectManager());
        coloredRect.setColor(pRed, pGreen, pBlue);
        return coloredRect;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
    }


    private boolean scheduleEngineStart = true;
    @Override
    public Engine onCreateEngine(EngineOptions pEngineOptions) {
        Engine engine = new Engine(pEngineOptions);
        if(scheduleEngineStart){
            engine.start();
            scheduleEngineStart = !scheduleEngineStart;
        }
        return engine;
    }

    @Override
    public synchronized void onResumeGame() {
        if(mEngine != null) {
            super.onResumeGame();
            scheduleEngineStart = true;
        }
        try {
            this.enableAccelerationSensor(GameplayActivity.this);
        }
        catch (Exception e) {
            Log.e("accel", "accel",e);
        }
    }

    @Override
    public void onPauseGame() {
        super.onPauseGame();
        this.disableAccelerationSensor();
    }

    @Override
    public void onPause() {
        super.onPause();
        Music.pauseBackgroundMusic();
    }

    @Override
    public void onResume() {
        super.onResume();
        Music.startBackgroundMusic(this);
    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
        if (pSceneTouchEvent.isActionDown()) {
            Random random = new Random();

            int[] sounds = new int[4];
            sounds[0] = R.raw.laser_gun;
            sounds[1] = R.raw.laser_gun;
            sounds[2] = R.raw.laser_gun;
            sounds[3] = R.raw.laser_gun;
            int soundid = sounds[random.nextInt(4)];
            Music.playSoundById(GameplayActivity.this, soundid);

            float x = pSceneTouchEvent.getX();
            float y = pSceneTouchEvent.getY();

            final Body body;
            Sprite sprite;
            sprite = new Sprite(x,y, this.mBoxFaceTextureRegion, this.getVertexBufferObjectManager());

            body = createBoatBody(this.mPhysicsWorld, sprite, BodyDef.BodyType.DynamicBody, FIXTURE_DEF);
            mScene.attachChild(sprite);
            boats.add(body);

            this.mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(sprite, body, true, true));

            /*mScene.attachChild(
                    makeColoredRectangle(
                            x - rWidth / 2,
                            y - rHeight / 2,
                            random.nextInt(255),
                            random.nextInt(255),
                            random.nextInt(255))
            );*/
        }
        return true;
    }

    @Override
    public void onAccelerationAccuracyChanged(final AccelerationData pAccelerationData) {

    }

    @Override
    public void onAccelerationChanged(final AccelerationData pAccelerationData) {
        //final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), pAccelerationData.getY());
        final Vector2 gravity = Vector2Pool.obtain(pAccelerationData.getX(), SensorManager.GRAVITY_MOON + pAccelerationData.getY());
        this.mPhysicsWorld.setGravity(gravity);
        Vector2Pool.recycle(gravity);
    }


    /**
     * Creates a {@link Body} based on a {@link PolygonShape} in the form of a hexagon:
     * <pre>
     *  /\
     * /  \
     * |  |
     * |  |
     * \  /
     *  \/
     * </pre>
     */
    private static Body createHexagonBody(final PhysicsWorld pPhysicsWorld, final IAreaShape pAreaShape, final BodyDef.BodyType pBodyType, final FixtureDef pFixtureDef) {
		/* Remember that the vertices are relative to the center-coordinates of the Shape. */
        final float halfWidth = pAreaShape.getWidthScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;
        final float halfHeight = pAreaShape.getHeightScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;

		/* The top and bottom vertex of the hexagon are on the bottom and top of hexagon-sprite. */
        final float top = -halfHeight;
        final float bottom = halfHeight;

        final float centerX = 0;

		/* The left and right vertices of the heaxgon are not on the edge of the hexagon-sprite, so we need to inset them a little. */
        final float left = -halfWidth + 2.5f / PIXEL_TO_METER_RATIO_DEFAULT;
        final float right = halfWidth - 8.25f / PIXEL_TO_METER_RATIO_DEFAULT;
        final float higher = top + 8.25f / PIXEL_TO_METER_RATIO_DEFAULT;
        final float lower = bottom - 8.25f / PIXEL_TO_METER_RATIO_DEFAULT;

        final Vector2[] vertices = {
                new Vector2(centerX, top),
                new Vector2(right, higher),
                new Vector2(right, lower),
                new Vector2(centerX, bottom),
                new Vector2(left, lower),
                new Vector2(left, higher)
        };

        return PhysicsFactory.createPolygonBody(pPhysicsWorld, pAreaShape, vertices, pBodyType, pFixtureDef);
    }

    private static Body createBoatBody(final PhysicsWorld pPhysicsWorld, final IAreaShape pAreaShape, final BodyDef.BodyType pBodyType, final FixtureDef pFixtureDef) {
        final float halfWidth = pAreaShape.getWidthScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;
        final float halfHeight = pAreaShape.getHeightScaled() * 0.5f / PIXEL_TO_METER_RATIO_DEFAULT;

        final float top = -halfHeight;
        final float bottom = halfHeight;

        final float left = -halfWidth+5f/PIXEL_TO_METER_RATIO_DEFAULT;
        final float right = halfWidth-5f/PIXEL_TO_METER_RATIO_DEFAULT;
        final float rightpoint = halfWidth;
        final float leftpoint = -halfWidth;
        final float higher = top;
        final float lower = bottom;

        final Vector2[] vertices = {
                new Vector2(right, higher),
                new Vector2(rightpoint,(higher+lower+1f/PIXEL_TO_METER_RATIO_DEFAULT)/2),
                new Vector2(right, lower),
                new Vector2(left, lower),
                new Vector2(leftpoint,(higher+lower-1f/PIXEL_TO_METER_RATIO_DEFAULT)/2),
                new Vector2(left, higher)
        };

        return PhysicsFactory.createPolygonBody(pPhysicsWorld, pAreaShape, vertices, pBodyType, pFixtureDef);
    }

    @Override
    public void onUpdate(float pSecondsElapsed) {
        for(Body boat : boats) {
            float magnitude = 2000f*pSecondsElapsed;
            float angle = boat.getAngle();
            Vector2 currentVelocity = new Vector2((float)Math.cos(angle)*magnitude, (float) Math.sin(angle)*magnitude);

            /*Vector2 currentVelocity = boat.getLinearVelocity();
            float forceX = currentVelocity.x + (1000f*pSecondsElapsed);
            float angle = boat.getAngle();
            currentVelocity.set(forceX, currentVelocity.y);*/
            boat.applyForce(currentVelocity,boat.getWorldCenter());
        }
    }

    @Override
    public void reset() {

    }
}
