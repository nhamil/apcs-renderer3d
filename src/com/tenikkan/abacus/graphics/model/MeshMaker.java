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
        
        for(int z = 0; z < length - 1; z++) 
        {
            float z0 = (float)(z)/(length - 0) * rangeZ + startZ;
            float z1 = (float)(z + 1)/(length - 0) * rangeZ + startZ;
            
            for(int x = 0; x < width - 1; x++) 
            {
                float x0 = (float)(x)/(width - 0) * rangeX + startX;
                float x1 = (float)(x + 1)/(width - 0) * rangeX + startX;
                
                int v00 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(x0, ht.getHeightPoint(x, z), z0), 
                                   new Vector4f(1, 1, 1, 1), 
                                   new Vector4f((x0 - startX)/rangeX, (z0 - startZ)/rangeZ, 0, 0)));
                int v01 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(x0, ht.getHeightPoint(x, z + 1), z1), 
                                   new Vector4f(1, 1, 1, 1), 
                                   new Vector4f((x0 - startX)/rangeX, (z1 - startZ)/rangeZ, 0, 0)));
                int v11 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(x1, ht.getHeightPoint(x + 1, z + 1), z1), 
                                   new Vector4f(1, 1, 1, 1), 
                                   new Vector4f((x1 - startX)/rangeX, (z1 - startZ)/rangeZ, 0, 0)));
                int v10 = model.addVertexCheckDuplicate(
                        new Vertex(new Vector4f(x1, ht.getHeightPoint(x + 1, z), z0), 
                                   new Vector4f(1, 1, 1, 1), 
                                   new Vector4f((x1 - startX)/rangeX, (z0 - startZ)/rangeZ, 0, 0)));
                
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
                    model.addIndex(v10);
                    
                    model.addIndex(v01);
                    model.addIndex(v11);
                    model.addIndex(v10);
                }
                
            }
        }
        
        return new Mesh(model);
    }
}
