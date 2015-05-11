/**
 * 
 */
package com.tenikkan.abacus.util;

import java.util.ArrayList;
import java.util.List;

import com.tenikkan.abacus.graphics.Vertex;
import com.tenikkan.abacus.graphics.model.Mesh;
import com.tenikkan.abacus.graphics.model.SimpleModel;
import com.tenikkan.abacus.math.Vector4f;

/**
 * @author Nicholas Hamilton
 *
 */
public class MeshMaker
{   
    public static Mesh generateHeightmapMesh(Heightmap ht) 
    {
        List<Vertex> vert = new ArrayList<Vertex>();
        List<Integer> ind = new ArrayList<Integer>();
        
        float dtx = 1f/ht.getPointsWide();
        float dty = 1f/ht.getPointsLong();
        
        float tx = 0;
        float ty = 0;
        
        float rangeX = ht.getEndX() - ht.getStartX();
        float rangeZ = ht.getEndZ() - ht.getStartZ();
        
        for(int z = 0; z < ht.getPointsLong() - 1; z++) 
        {
            float rz = (float)z / ht.getPointsLong();
            float rz1 = (float)(z+1) / ht.getPointsLong();
            for(int x = 0; x < ht.getPointsWide() - 1; x++) 
            {
                float rx = (float)x / ht.getPointsWide();
                float rx1 = (float)(x+1) / ht.getPointsWide();
//                tx = 0;
//                ty = 0;
//                dtx = 1;
//                dty = 1;
                
                Vertex v00 = new Vertex(new Vector4f(rx*rangeX, ht.getHeightPoint(x, z), rz*rangeZ), new Vector4f(tx, ty, 0, 0));
                Vertex v01 = new Vertex(new Vector4f(rx*rangeX, ht.getHeightPoint(x, z+1), rz1*rangeZ), new Vector4f(tx, (ty + dty), 0, 0));
                Vertex v11 = new Vertex(new Vector4f(rx1*rangeX, ht.getHeightPoint(x+1, z+1), rz1*rangeZ), new Vector4f((tx + dtx), (ty + dty), 0, 0));
                Vertex v10 = new Vertex(new Vector4f(rx1*rangeX, ht.getHeightPoint(x+1, z), rz*rangeZ), new Vector4f((tx + dtx), ty, 0, 0));
                
                int id00 = getIndex(vert, v00);
                int id01 = getIndex(vert, v01);
                int id11 = getIndex(vert, v11);
                int id10 = getIndex(vert, v10);
                
                if((x + z) % 2 == 0) 
                {
                    ind.add(id00); ind.add(id11); ind.add(id10);
                    ind.add(id00); ind.add(id01); ind.add(id11);
                } else 
                {
                    ind.add(id00); ind.add(id01); ind.add(id10);
                    ind.add(id01); ind.add(id11); ind.add(id10);
                }
                
                tx += dtx;
            }
            ty += dty;
            tx = 0;
        }
        
        return new Mesh(new SimpleModel(vert, ind));
    }
    
    private static int getIndex(List<Vertex> vert, Vertex v) 
    {
        for(int i = 0; i < vert.size(); i++) 
        {
            if(vert.get(i).equals(v)) return i;
        }
        vert.add(v);
        return vert.size() - 1;
    }
}
