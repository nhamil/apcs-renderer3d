package com.tenikkan.abacus;

import static com.tenikkan.abacus.graphics.ab.AB.*;

import com.tenikkan.abacus.graphics.Bitmap;
import com.tenikkan.abacus.graphics.Display;
import com.tenikkan.abacus.graphics.model.Mesh;
import com.tenikkan.abacus.graphics.model.MeshMaker;
import com.tenikkan.abacus.input.Keyboard;
import com.tenikkan.abacus.input.Mouse;
import com.tenikkan.abacus.util.Console;
import com.tenikkan.abacus.util.GameLoop;
import com.tenikkan.abacus.util.Heightmap;

public class HeightmapDemo extends GameLoop
{
    private Display display;
    private String title = "Abacus 3D Terrain Demo";
    
    private Bitmap heightmapBitmap, waterBitmap;
    
    private Heightmap heightmap, water;
    
    private Mesh mesh, waterMesh;
    
    private Keyboard keyboard;
    @SuppressWarnings("unused")
    private Mouse mouse;
    
    public static void main(String args[]) 
    {
        new HeightmapDemo().run();
    }
    
    public HeightmapDemo()
    {
        super(-1, 60);
    }

    @Override
    public void init()
    {
        Console.show();
        
        display = new Display(title, 800, 600, 800, 600);
        display.show();
        
        keyboard = display.getKeyboard();
        mouse = display.getMouse();
        
        heightmap = new Heightmap(64, 64, -64f, -64f, 64f, 64f);
        heightmap.generateRandomHeightmap(); 
        
        water = new Heightmap(8, 8, -64f, -64f, 64f, 64f);
        water.generateRandomHeightmap(); 
        
        heightmapBitmap = heightmap.toBitmap(0, 1, 0);
        waterBitmap = water.toBitmap(0, 0, 1);
        
        mesh = MeshMaker.generateHeightmapMesh(heightmap);
        waterMesh = MeshMaker.generateHeightmapMesh(water);
        
        initABContext();
    }
    
    private void initABContext() 
    {
        abSetContext(display.getScreen());
        
        abClearColor3i(100, 200, 255);
        
        abMatrixMode(AB_PROJECTION);
        abLoadIdentity();
        abPerspective(70, display.getRatio(), 0.1f, 1000f);
        
        abMatrixMode(AB_MODELVIEW);
        abLoadIdentity();
        
        abEnable(AB_CULLING);
        abDisable(AB_COLOR);
        abEnable(AB_TEXTURE_MAPPING);
        abEnable(AB_DEPTH_TESTING);
    }
    
    float ticks = 0;
    float updown = 0;
    float inout = 60;
    
    @Override
    public void update()
    {
        if(keyboard.isKeyDown(Keyboard.LEFT)) ticks += 1;
        if(keyboard.isKeyDown(Keyboard.RIGHT)) ticks -= 1;
        if(keyboard.isKeyDown(Keyboard.UP)) updown -= 1;
        if(keyboard.isKeyDown(Keyboard.DOWN)) updown += 1;
        if(keyboard.isKeyDown(Keyboard.FORWARD)) inout -= 1;
        if(keyboard.isKeyDown(Keyboard.BACKWARD)) inout += 1;
        display.setTitle(title + " - " + getData());
    }

    @Override
    public void render()
    {
        abClear(AB_FLAG_COLOR_BUFFER | AB_FLAG_DEPTH_BUFFER);
        abLoadIdentity();
        
        abRotate3f(0, ticks / 2f, 0);
        abRotate3f(updown, 0, 0);
        abTranslate3f(0, -5, inout);
        
        abLoadTexture(heightmapBitmap);
        abDrawMesh(mesh);
        
        abLoadTexture(waterBitmap);
        abDrawMesh(waterMesh);
        
        display.render();
    }
    
}
