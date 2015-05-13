package com.tenikkan.abacus.graphics.model;

import com.tenikkan.abacus.math.Vector4f;
import com.tenikkan.abacus.util.Heightmap;

public class MeshMaker
{
    public static Mesh generateHeightmapMesh(Heightmap ht) 
    {
        ArrayModel model = new ArrayModel();
        
        int width = ht.getPointsWide();
        int length = ht.getPointsLong();
        
        float startX = ht.getStartX();
        float startZ = ht.getStartZ();
        float endX   = ht.getEndX();
        float endZ   = ht.getEndZ();
        float rangeX = endX - startX;
        float rangeZ = endZ - startZ;
        float dx     = rangeX / width;
        float dz     = rangeZ / length;
        
        float xPos = startX;
        float zPos = startZ;
        
        for(int z = 0; z < length - 1; z++) 
        {
            xPos = startX;
            for(int x = 0; x < width - 1; x++) 
            {
                int v00 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(xPos, ht.getHeightPoint(x, z), zPos), 
                                   new Vector4f(1, 1, 1, 1), 
                                   new Vector4f((xPos - startX)/rangeX, (zPos - startZ)/rangeZ, 0, 0)));
                int v01 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(xPos, ht.getHeightPoint(x, z + 1), zPos + dz), 
                                   new Vector4f(1, 0, 0, 1), 
                                   new Vector4f((xPos - startX)/rangeX, (zPos + dz - startZ)/rangeZ, 0, 0)));
                int v11 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(xPos + dx, ht.getHeightPoint(x + 1, z + 1), zPos + dz), 
                                   new Vector4f(0, 1, 0, 1), 
                                   new Vector4f((xPos + dx - startX)/rangeX, (zPos + dz - startZ)/rangeZ, 0, 0)));
                int v10 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(xPos + dx, ht.getHeightPoint(x + 1, z), zPos), 
                                   new Vector4f(0, 0, 1, 1), 
                                   new Vector4f((xPos + dx - startX)/rangeX, (zPos - startZ)/rangeZ, 0, 0)));
                
                if((x + z) % 2 == 1) 
                {
                    model.addIndex(v00);
                    model.addIndex(v01);
                    model.addIndex(v11);
                    
                    model.addIndex(v00);
                    model.addIndex(v11);
                    model.addIndex(v10);
                } else 
                {
                    model.addIndex(v00);
                    model.addIndex(v01);
                    model.addIndex(v11);
                    
                    model.addIndex(v00);
                    model.addIndex(v11);
                    model.addIndex(v10);
                }
                
                xPos += dx;
            }
            zPos += dz;
        }
        
        return new Mesh(model);
    }
}
