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
package org.terasology.entitySystem;

import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.terasology.engine.bootstrap.EntitySystemBuilder;
import org.terasology.engine.module.ModuleManager;
import org.terasology.entitySystem.stubs.OwnerComponent;
import org.terasology.network.NetworkSystem;

import static org.mockito.Mockito.mock;
import static org.terasology.testUtil.TeraAssert.assertEqualsContent;

/**
 * @author Immortius
 */
public class OwnershipHelperTest {

    EngineEntityManager entityManager;

    private static ModuleManager moduleManager;

    @BeforeClass
    public static void setupClass() {
        moduleManager = new ModuleManager();
    }

    @Before
    public void setup() {
        EntitySystemBuilder builder = new EntitySystemBuilder();

        entityManager = builder.build(moduleManager, mock(NetworkSystem.class));
    }

    @Test
    public void listsOwnedEntities() {
        EntityRef ownedEntity = entityManager.create();
        OwnerComponent ownerComp = new OwnerComponent();
        ownerComp.child = ownedEntity;
        EntityRef ownerEntity = entityManager.create(ownerComp);

        OwnershipHelper helper = new OwnershipHelper(entityManager.getComponentLibrary());
        assertEqualsContent(Lists.newArrayList(ownedEntity), helper.listOwnedEntities(ownerEntity));
    }
}
