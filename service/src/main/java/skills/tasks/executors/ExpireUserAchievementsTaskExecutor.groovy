/**
 * Copyright 2020 SkillTree
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package skills.tasks.executors

import com.github.kagkarlsson.scheduler.task.ExecutionContext
import com.github.kagkarlsson.scheduler.task.TaskInstance
import com.github.kagkarlsson.scheduler.task.VoidExecutionHandler
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.time.StopWatch
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import skills.services.admin.UserAchievementExpirationService
import skills.services.attributes.SkillAttributeService
import skills.tasks.data.UnachievableLevelIdentification

import java.util.concurrent.TimeUnit

@Slf4j
@Component
class ExpireUserAchievementsTaskExecutor implements VoidExecutionHandler<UnachievableLevelIdentification> {

    private static final long LOGGING_THRESHOLD = 5000

    @Autowired
    SkillAttributeService skillAttributeService

    @Autowired
    UserAchievementExpirationService userAchievementExpirationService

    @Override
    void execute(TaskInstance<UnachievableLevelIdentification> taskInstance, ExecutionContext executionContext) {
        StopWatch stopWatch = new StopWatch()
        stopWatch.start()
        try {
            log.info("Checking for expiring user achievements.")
            userAchievementExpirationService.removeExpiredUserAchievements()
        } finally {
            stopWatch.stop()
            long runTime = stopWatch.getTime(TimeUnit.MILLISECONDS)
            if (runTime > LOGGING_THRESHOLD) {
                log.info("Expiring user achievements took [${runTime}]ms")
            }
        }
    }
}
