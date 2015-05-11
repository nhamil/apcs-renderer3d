package com.tenikkan.abacus;

import com.tenikkan.abacus.graphics.Bitmap;
import com.tenikkan.abacus.graphics.Camera;
import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.graphics.Renderer;
import com.tenikkan.abacus.graphics.Vertex;
import com.tenikkan.abacus.graphics.model.Mesh;
import com.tenikkan.abacus.input.Keyboard;
import com.tenikkan.abacus.input.Mouse;
import com.tenikkan.abacus.math.Matrix4f;
import com.tenikkan.abacus.math.Transform;
import com.tenikkan.abacus.math.Vector4f;
import com.tenikkan.abacus.util.Console;
import com.tenikkan.abacus.util.GameLoop;
import com.tenikkan.abacus.util.Heightmap;
import com.tenikkan.abacus.util.MeshMaker;
import com.tenikkan.abacus.util.Timer;

/**
 * 
 * Main class for the game, updating and rendering is done here.
 * 
 * @author Nicholas Hamilton
 *
 */

public class Game extends GameLoop
{
    private Timer timer;
    private Display display;
    
    private Bitmap bmp, gem, land, waterImg;
    private Mesh mesh, terrain, water;
    private Transform transform;
    
    private String title = "Abacus Engine";
    
    public Game()
    {
        super(-1, 60);
    }
    
    public void init()
    {
        Console.outln("Initializing...", Console.DEBUG);
        
        timer = new Timer();
        
        // 1920 1020
        // 1280 1000
        // 380  285
        display = new Display(title, 800, 600, 380, 285);
        display.show();
        display.getScreen().setCullingEnabled(true);
        display.hideCursor();
        
        transform = new Transform();
        transform.setPerspective(70f, (float)display.getWidth()/display.getHeight(), 0.1f, 100f);
        
        Console.outln("Done!", Console.DEBUG);
        
        bmp = new Bitmap("res/texture/brick_real_2.png");
        gem = new Bitmap("res/texture/diamond.png");
        land = new Bitmap("res/texture/grass_real_2.png");
        waterImg = new Bitmap("res/texture/water.png");
        
        mesh = new Mesh("res/model/box.nm");
        water = new Mesh("res/model/ground.nm");
        
        Heightmap ht = new Heightmap(32, 32, -128f, -128f, 128f, 128f);
        ht.generateRandomHeightmap();
        terrain = MeshMaker.generateHeightmapMesh(ht);
        
        Camera cam = transform.getCamera();
        cam.setPosition(new Vector4f(0f, 0f, 2f, 0f));
    }
    
    public void update()
    {
        timer.tick();
        if(timer.getDelta() >= Timer.SECOND)
        {
            timer.resetTime();
            display.setTitle(title + " - " + getData());
        }
        
        Mouse mouse = display.getMouse();
        Keyboard keys = display.getKeyboard();
        keys.update();
        
        Camera cam = transform.getCamera();
        
        float move = 0.14f; 
        if(keys.isKeyDown(Keyboard.FORWARD)) cam.move(cam.getForwardNoY(), move);
        if(keys.isKeyDown(Keyboard.BACKWARD)) cam.move(cam.getForwardNoY(), -move);
        if(keys.isKeyDown(Keyboard.LEFT)) cam.move(cam.getLeft(), move);
        if(keys.isKeyDown(Keyboard.RIGHT)) cam.move(cam.getRight(), move);
        if(keys.isKeyDown(Keyboard.UP)) cam.move(Camera.Y_AXIS, move);
        if(keys.isKeyDown(Keyboard.DOWN)) cam.move(Camera.Y_AXIS, -move);
        
        if(keys.isKeyDown(Keyboard.ESCAPE)) System.exit(0);
        
        int oldX = mouse.getGlobalX();
        int oldY = mouse.getGlobalY();
        int xPos = display.getFullWidth()/2 + display.getX();
        int yPos = display.getFullHeight()/2 + display.getY();
        display.setMousePosition(xPos, yPos);
        int changeX = oldX - mouse.getGlobalX();
        float rotX = (float)changeX/700f;//display.getFullWidth();
        int changeY = oldY - mouse.getGlobalY();
        float rotY = (float)changeY/700f;//display.getFullHeight();
        cam.rotateY(rotX);
        cam.rotateX(rotY);
    }
    
    public void render()
    {
        Renderer render = display.getScreen();
        render.clear(0x66bbff);
        render.setCullingEnabled(false);
        
        render.setTexture(bmp);
        transform.setTranslation(0, -2, -2);
        transform.setRotation(timer.getTicks()/2.7f, timer.getTicks()/2.4f, timer.getTicks()/2.9f);
        transform.setScale(3, 3, 3);
        mesh.render(render, transform);
        
        render.setTexture(waterImg);
        transform.setTranslation(0, -3, 0);
        transform.setRotation(0, 0, 0);
        transform.setScale(50, 1, 50);
        water.render(render, transform);
        
        {
            render.setTexture(gem);
            transform.setRotation(0, 0, 0);
            transform.setTranslation(2, -1, 6);
            transform.setScale(1, 1, 1);
            Matrix4f m = transform.getMatrix();
            Vertex v1 = new Vertex(new Vector4f(-1, 1, 0), new Vector4f(0, 1, 0, 0));
            Vertex v2 = new Vertex(new Vector4f( 1, 1, 0), new Vector4f(1, 1, 0, 0));
            Vertex v3 = new Vertex(new Vector4f( 1,-1, 0), new Vector4f(1, 0, 0, 0));
            Vertex v4 = new Vertex(new Vector4f(-1,-1, 0), new Vector4f(0, 0, 0, 0));
            render.drawRectangle(v1.getTransform(m), v2.getTransform(m), v3.getTransform(m), v4.getTransform(m));
            transform.setRotation(0, 180, 0);
            transform.setTranslation(2, -1, 8);
            m = transform.getMatrix();
            render.drawRectangle(v1.getTransform(m), v2.getTransform(m), v3.getTransform(m), v4.getTransform(m));
            transform.setRotation(0, 90, 0);
            transform.setTranslation(3, -1, 7);
            m = transform.getMatrix();
            render.drawRectangle(v1.getTransform(m), v2.getTransform(m), v3.getTransform(m), v4.getTransform(m));
            transform.setRotation(0,-90, 0);
            transform.setTranslation(1, -1, 7);
            m = transform.getMatrix();
            render.drawRectangle(v1.getTransform(m), v2.getTransform(m), v3.getTransform(m), v4.getTransform(m));
            transform.setRotation(90, 0, 0);
            transform.setTranslation(2, 0, 7);
            m = transform.getMatrix();
            render.drawRectangle(v1.getTransform(m), v2.getTransform(m), v3.getTransform(m), v4.getTransform(m));
            transform.setRotation(-90, 0, 0);
            transform.setTranslation(2, -2, 7);
            m = transform.getMatrix();
            render.drawRectangle(v1.getTransform(m), v2.getTransform(m), v3.getTransform(m), v4.getTransform(m));
        }
        
        render.setTexture(land);
        transform.setTranslation(-5, -2, -5);
        transform.setRotation(0, 0, 0);
        transform.setScale(1, 1, 1);
        terrain.render(render, transform);
        
        display.render();
    }
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    @SuppressWarnings("unused")
    private void speedTesting(Renderer render) 
    {
        Matrix4f rotate = new Matrix4f().initRotation(0, timer.getTicks()/0.2f, 0);//tick, tick, tick);
        Matrix4f pos = new Matrix4f().initTranslation(0, 0, 3 + 5*(float)Math.cos(timer.getTicks()/100f));
        Matrix4f proj = new Matrix4f().initPerspective(70f, (float)render.getWidth()/render.getHeight(), 0.1f, 1000f);
        Matrix4f t = rotate.mul(pos.mul(proj));
        
        float amt = 1;
        Vector4f top = new Vector4f(0, amt, 0, 1.0f);
        Vector4f left = new Vector4f(-amt, -amt, 0, 1.0f);
        Vector4f right = new Vector4f(amt, -amt, 0, 1.0f);
        
        Vertex vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        Vertex vLeft = new Vertex(t.mul(left),   new Vector4f(0f, 0f, 0f, 0f));
        Vertex vRight = new Vertex(t.mul(right), new Vector4f(1f, 0f, 0f, 0f));
        
        render.drawTriangle(vTop, vLeft, vRight);
        render.drawTriangle(vTop, vRight, vLeft);
        
        drawLand(render);
        
        float rx = 0.0f;
        float ry = -0.8f;// - timer.getTicks()*0.01f;
        float rz = 2.5f - timer.getTicks()*0.01f;
        
        render.setTexture(gem);
        for(int z = 0; z < 40; z++) 
        {
            renderPyramid(render, rx + 0.0f, ry + z*0.1f, rz + z*0.5f);
            renderPyramid(render, rx - 1.0f, ry + z*0.1f, rz + z*0.5f);
            renderPyramid(render, rx - 2.0f, ry + z*0.1f, rz + z*0.5f);
            renderPyramid(render, rx + 1.0f, ry + z*0.1f, rz + z*0.5f);
            renderPyramid(render, rx + 2.0f, ry + z*0.1f, rz + z*0.5f);
        }
    }
    
    private void drawLand(Renderer render) 
    {
        Matrix4f rotate = new Matrix4f().initRotation(30, 0, 0);//tick, tick, tick);
        Matrix4f pos = new Matrix4f().initTranslation(0, -1f, 3);
        Matrix4f proj = new Matrix4f().initPerspective(70f, (float)render.getWidth()/render.getHeight(), 0.1f, 1000f);
        
        Matrix4f t = rotate.mul(pos.mul(proj));
        
        float amt = 10f;
        
        Vector4f tl = new Vector4f(-amt, amt, 0, 1.0f);
        Vector4f tr = new Vector4f( amt, amt, 0, 1.0f);
        Vector4f bl = new Vector4f(-amt,-amt, 0, 1.0f);
        Vector4f br = new Vector4f( amt,-amt, 0, 1.0f);
        
        Vertex vTL = new Vertex(t.mul(tl), new Vector4f(0f, 1f, 0f, 0f));
        Vertex vTR = new Vertex(t.mul(tr), new Vector4f(1f, 1f, 0f, 0f));
        Vertex vBR = new Vertex(t.mul(br), new Vector4f(1f, 0f, 0f, 0f));
        Vertex vBL = new Vertex(t.mul(bl), new Vector4f(0f, 0f, 0f, 0f));
        
        render.setTexture(land);
        render.drawTriangle(vTL, vTR, vBR);
        render.drawTriangle(vTL, vBR, vBL);
    }
    
    private void renderPyramid(Renderer render, float x, float y, float z) 
    {
        float tick = (timer.getTicks()) / 0.5f;
        float amt = 0.4f;
        
        Matrix4f rotate = new Matrix4f().initRotation(-60, tick, 0);//tick, tick, tick);
        Matrix4f pos = new Matrix4f().initTranslation(x, y, z);
        Matrix4f proj = new Matrix4f().initPerspective(70f, (float)render.getWidth()/render.getHeight(), 0.1f, 1000f);
        
        Matrix4f t = rotate.mul(pos.mul(proj));
        
        Vector4f top = new Vector4f(0, amt, 0, 1.0f);
        Vector4f left = new Vector4f(-amt, -amt, -amt, 1.0f);
        Vector4f right = new Vector4f(amt, -amt, -amt, 1.0f);
        
        Vertex vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        Vertex vLeft = new Vertex(t.mul(left),   new Vector4f(0f, 0f, 0f, 0f));
        Vertex vRight = new Vertex(t.mul(right), new Vector4f(1f, 0f, 0f, 0f));
        
        render.drawTriangle(vTop, vRight, vLeft);

        vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        vLeft = new Vertex(t.mul(left.mul(1,1,-1,1)),   new Vector4f(0f, 0f, 0f, 0f));
        vRight = new Vertex(t.mul(right.mul(1,1,-1,1)), new Vector4f(1f, 0f, 0f, 0f));
        render.drawTriangle(vTop, vLeft, vRight);
        
        vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        vLeft = new Vertex(t.mul(left.mul(-1,1,1,1)),   new Vector4f(0f, 0f, 0f, 0f));
        vRight = new Vertex(t.mul(right.mul(1,1,-1,1)), new Vector4f(1f, 0f, 0f, 0f));
        render.drawTriangle(vTop, vRight, vLeft);
        
        vTop = new Vertex(t.mul(top),     new Vector4f(0.5f, 1f, 0f, 0f));
        vLeft = new Vertex(t.mul(left.mul(1,1,1,1)),   new Vector4f(0f, 0f, 0f, 0f));
        vRight = new Vertex(t.mul(right.mul(-1,1,-1,1)), new Vector4f(1f, 0f, 0f, 0f));
        render.drawTriangle(vTop, vLeft, vRight);
    }
}
