/*
 * Copyright 2013 Moving Blocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.benchmark.chunks.arrays;

import org.terasology.world.chunks.blockdata.TeraArray;

/**
 * BenchmarkTeraArrayRead implements a simple read performance benchmark for tera arrays.
 * 
 * @author Manuel Brotz <manu.brotz@gmx.ch>
 *
 */
public class BenchmarkTeraArrayRead extends BenchmarkTeraArray {

    public BenchmarkTeraArrayRead(TeraArray array) {
        super(array);
    }
    
    @Override
    public String getTitle() {
        return array.getClass().getSimpleName() + " read performance";
    }

    @Override
    public void run() {
        int tmp = 0;
        for (int y = 0; y < array.getSizeY(); y++) {
            for (int z = 0; z < array.getSizeZ(); z++) {
                for (int x = 0; x < array.getSizeX(); x++) {
                    tmp += array.get(x, y, z);
                    array.get(x, y, z);
                }
            }
        }
    }

}
