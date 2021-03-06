/*
 * Copyright 2016 MovingBlocks
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
package org.terasology.rendering.dag.stateChanges;

import org.terasology.assets.ResourceUrn;
import org.terasology.rendering.opengl.BaseFBOsManager;
import org.terasology.rendering.opengl.DefaultDynamicFBOs;
import org.terasology.rendering.opengl.FBOManagerSubscriber;
import java.util.Objects;
import org.terasology.rendering.dag.RenderPipelineTask;
import org.terasology.rendering.dag.StateChange;
import org.terasology.rendering.dag.tasks.SetViewportToSizeOfTask;
import org.terasology.rendering.opengl.FBO;
import static org.terasology.rendering.opengl.DefaultDynamicFBOs.READ_ONLY_GBUFFER;
/**
 * TODO: Add javadocs
 */
public final class SetViewportToSizeOf implements FBOManagerSubscriber, StateChange {
    private static SetViewportToSizeOf defaultInstance = new SetViewportToSizeOf(READ_ONLY_GBUFFER);

    private BaseFBOsManager frameBuffersManager;
    private SetViewportToSizeOfTask task;
    private ResourceUrn fboName;
    private DefaultDynamicFBOs defaultDynamicFBO;

    public SetViewportToSizeOf(ResourceUrn fboName, BaseFBOsManager frameBuffersManager) {
        this.frameBuffersManager = frameBuffersManager;
        this.fboName = fboName;
    }

    public SetViewportToSizeOf(DefaultDynamicFBOs defaultDynamicFBO) {
        this.defaultDynamicFBO = defaultDynamicFBO;
        this.frameBuffersManager = defaultDynamicFBO.getFrameBufferManager();
        this.fboName = defaultDynamicFBO.getName();
    }

    @Override
    public StateChange getDefaultInstance() {
        return defaultInstance;
    }

    @Override
    public RenderPipelineTask generateTask() {
        if (task == null) {
            task = new SetViewportToSizeOfTask(fboName);
            frameBuffersManager.subscribe(this);
            update();
        }

        return task;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(fboName);
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof SetViewportToSizeOf) && this.fboName.equals(((SetViewportToSizeOf) obj).getFboName());
    }

    @Override
    public boolean isTheDefaultInstance() {
        return this == defaultInstance;
    }

    @Override
    public void update() {
        if (defaultDynamicFBO == null) {
            FBO fbo = frameBuffersManager.get(fboName);
            task.setDimensions(fbo.width(), fbo.height());
        } else {
            task.setDimensions(defaultDynamicFBO.width(), defaultDynamicFBO.height());
        }
    }

    @Override
    public String toString() { // TODO: used for logging purposes at the moment, investigate different methods
        return String.format("%30s: %s", this.getClass().getSimpleName(), fboName);
    }

    public ResourceUrn getFboName() {
        return fboName;
    }
}
