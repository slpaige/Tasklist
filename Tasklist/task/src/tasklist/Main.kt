package tasklist
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.datetime.*
import java.io.File
import java.lang.reflect.ParameterizedType

var taskList = mutableListOf<Task>()
val priorityList = arrayOf("C","H","N","L")

class Task() {
    var taskLines = mutableListOf<String>()
    var priority = ""
    var date = ""
    var time = ""

    fun getDueTag() : String {
        val dateSplit = date.split("-")
        val taskDate = LocalDate(dateSplit[0].toInt(), dateSplit[1].toInt(), dateSplit[2].toInt())
        val currentDate = Clock.System.now().toLocalDateTime(TimeZone.of("UTC+2")).date
        val numberOfDays = currentDate.daysUntil(taskDate)

        if(numberOfDays == 0) {
            return "T"
        } else if (numberOfDays > 0) {
            return "I"
        } else {
            return "O"
        }
    }

    fun getDueTagColor() : String {
        val tag = getDueTag()
        when(tag.uppercase()) {
            "I" -> return "\u001B[102m \u001B[0m"
            "T" -> return "\u001B[103m \u001B[0m"
            "O" -> return "\u001B[101m \u001B[0m"
            else -> return tag.uppercase()
        }
    }

    fun getPriorityColor(priority: String) : String {
        return when(priority.uppercase()) {
            "C" -> "\u001B[101m \u001B[0m"
            "H" -> "\u001B[103m \u001B[0m"
            "N" -> "\u001B[102m \u001B[0m"
            "L" -> "\u001B[104m \u001B[0m"
            else -> priority.uppercase()
        }
    }

    fun addTaskLine(line: String) {
        taskLines.add(line)
    }

    fun setTaskPriority() {
        while(true) {
            println("Input the task priority (C, H, N, L):")
            var taskPriority = readln()

            if(priorityList.contains(taskPriority.uppercase())) {
                priority = taskPriority.uppercase()
                break
            }
        }
    }

    fun setDate() {
        while(true) {
            println("Input the date (yyyy-mm-dd):")
            var taskDate = readln().split("-")

            if(taskDate.size != 3) {
                println("The input date is invalid")
            } else {
                val year = taskDate[0]
                val month = taskDate[1].padStart(2,'0')
                val day = taskDate[2].padStart(2,'0')

                try {
                    Instant.parse("$year-$month-${day}T00:00:00Z")
                    date = "$year-$month-$day"
                    break
                } catch (e: Exception) {
                    println("The input date is invalid")
                }
            }
        }
    }

    fun setTime() {
        while(true) {
            println("Input the time (hh:mm):")
            var taskTime = readln().split(":")

            if(taskTime.size != 2) {
                println("The input time is invalid")
            } else {
                val hour = taskTime[0].padStart(2,'0')
                val minute = taskTime[1].padStart(2,'0')

                try {
                    Instant.parse("${date}T$hour:$minute:00Z")
                    time = "$hour:$minute"
                    break
                } catch (e: Exception) {
                    println("The input time is invalid")
                }
            }
        }
    }

    fun setTaskLines(){
        println("Input a new task (enter a blank line to end):")
        taskLines.clear()

        while(true) {
            val taskItem = readln().trim()
            if(taskItem == "") {
                break
            } else {
                if(taskItem.replace("\t", "").isEmpty() || taskItem.trim().isEmpty()) {
                    println("The task is blank")
                } else {
                    addTaskLine(taskItem)
                }
            }
        }
    }
}

fun editTask() {
    if(!taskList.any()){
        println("No tasks have been input")
        return
    }

    printTasks()

    while(true) {
        println("Input the task number (1-${taskList.size}):")

        try {
            var taskIndex = readln().toInt()
            if(taskIndex > taskList.size || taskIndex < 1) {
                println("Invalid task number")
            } else {
                //valid number, field input loop
                val editTask = taskList.get(taskIndex - 1)

                while (true) {
                    println("Input a field to edit (priority, date, time, task):")
                    val editField = readln()

                    when(editField.lowercase()) {
                        "priority" -> {
                            editTask.setTaskPriority()
                            break
                        }
                        "date" -> {
                            editTask.setDate()
                            break
                        }
                        "time" -> {
                            editTask.setTime()
                            break
                        }
                        "task" -> {
                            editTask.setTaskLines()
                            break
                        }
                        else -> {
                            println("Invalid field")
                        }
                    }
                }
                println("The task is changed")
                taskList[taskIndex - 1] = editTask
                break
            }
        } catch(e: Exception) {
            println("Invalid task number")
        }
    }
}


fun deleteTask() {
    if(!taskList.any()){
        println("No tasks have been input")
        return
    }

    printTasks()

    while(true) {
        println("Input the task number (1-${taskList.size}):")

        try {
            var taskIndex = readln().toInt()
            if(taskIndex > taskList.size || taskIndex < 1) {
                println("Invalid task number")
            } else {
                taskList.removeAt(taskIndex - 1)
                println("The task is deleted")
                break
            }
        } catch(e: Exception) {
            println("Invalid task number")
        }
    }
}




fun addTask() {
    val task = Task()
    //ask for priority
    task.setTaskPriority()

    //ask for date
    task.setDate()

    //ask for time
    task.setTime()

    //ask for task entry
    task.setTaskLines()

    //not sure if this is in the right place???
    if(task.taskLines.any()) {
        taskList.add(task)
    } else {
        println("The task is blank")
    }
}

fun printTasks() {
    if(!taskList.any()){
        println("No tasks have been input")
        return
    }

    //print header
    println("+----+------------+-------+---+---+--------------------------------------------+")
    println("| N  |    Date    | Time  | P | D |                   Task                     |")
    println("+----+------------+-------+---+---+--------------------------------------------+")
    for (i in taskList.withIndex()) {
        val num = " ${(i.index + 1).toString().padEnd(3,' ')}"
        val date = " ${i.value.date} "
        val time = " ${i.value.time} "
        val pri = " ${i.value.getPriorityColor(i.value.priority)} "
        val due = " ${i.value.getDueTagColor()} "
        val joined = "|$num|$date|$time|$pri|$due|"

        for (line in i.value.taskLines.withIndex()) {
            val chunkedTasksLines = line.value.chunked(44)
            for (chunk in chunkedTasksLines.withIndex()) {
                if(chunk.index == 0 && line.index == 0) {
                    //print the first line of a task, but only for first chunk and first instance of task
                    println("$joined${chunk.value.padEnd(44,' ')}|")
                } else {
                    //print the additional lines of a task if the line exceeded 44 chars
                    println("|    |            |       |   |   |${chunk.value.padEnd(44,' ')}|")
                }
            }
        }
        //print the bottom of the row
        println("+----+------------+-------+---+---+--------------------------------------------+")
    }
}


fun loadJson() {
    val file = File("tasklist.json")

    if(file.exists()){
        val taskJson = file.readText()
        //load it using moshi
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        //create adapter
        val type: ParameterizedType = Types.newParameterizedType(List::class.java, Task::class.java)
        val taskListAdapter: JsonAdapter<List<Task>> = moshi.adapter(type)

        val newTaskList = taskListAdapter.fromJson(taskJson)!!.toList()
        for (task in newTaskList){
            taskList.add(task)
        }
    }
}

fun saveJson() {
    if(taskList.any()){
        //create builder
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        //create adapter
        val type: ParameterizedType = Types.newParameterizedType(List::class.java, Task::class.java)
        val taskListAdapter: JsonAdapter<List<Task>> = moshi.adapter(type)

        val file = File("tasklist.json")
        file.writeText(taskListAdapter.toJson(taskList))
    }
}

fun main() {

    //try to read data if it exists and pre-load it
    loadJson()

    while (true) {
       println("Input an action (add, print, edit, delete, end):")
       when (readln()) {
           "add" -> addTask()
           "print" -> printTasks()
           "edit" -> editTask()
           "delete" -> deleteTask()
           "end" -> {
               println("Tasklist exiting!")
               //save data
               saveJson()
               break
           }
           else -> {
               println("The input action is invalid")
               continue
           }
       }
   }
}


