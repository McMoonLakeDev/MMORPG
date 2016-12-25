/*
 * Copyright (C) 2016 The MoonLake Authors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
 
  
package com.minecraft.moonlake.mmorpg.task;

import com.minecraft.moonlake.mmorpg.api.MMORPG;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoonLake on 2016/5/25.
 */
public class MMORPGTaskManager {

    private final MMORPG main;
    private final List<MMORPGTask> taskList;

    public MMORPGTaskManager(MMORPG main) {

        this.main = main;
        this.taskList = new ArrayList<>();
        this.registerDefault();
    }

    private void registerDefault() {

        taskList.add(new MagicRegainTask(main));
    }

    /**
     * 关闭月色之湖大型多人在线角色扮演所有任务
     */
    public void close() {

        for(MMORPGTask task : taskList) {

            if(task != null) {

                task.cancel();
            }
        }
        taskList.clear();
    }
}
