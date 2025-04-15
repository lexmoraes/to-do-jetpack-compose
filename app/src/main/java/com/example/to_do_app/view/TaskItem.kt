package com.application.intro.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.application.intro.R
import com.example.to_do_app.model.TaskModel
import com.example.to_do_app.ui.theme.GreenRadioButtonSelected
import com.example.to_do_app.ui.theme.RedRadioButtonSelected
import com.example.to_do_app.ui.theme.ShapePriorityCard
import com.example.to_do_app.ui.theme.YellowRadioButtonSelected

@Composable
fun TaskItem(
    task: TaskModel
) {
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Unspecified,
            disabledContentColor = Color.Unspecified,
            disabledContainerColor = Color.Unspecified
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(20.dp)
        ) {
            val (
                txtTaskTitle, txtTaskDescription,
                txtTaskPriority, cardTaskPriorityColor,
                btnDelete
            ) = createRefs()
            Text(
                text = task.title!!,
                modifier = Modifier.constrainAs(txtTaskTitle) {
                    top.linkTo(
                        parent.top,
                        margin = 10.dp
                    )
                    start.linkTo(
                        parent.start,
                        margin = 10.dp
                    )
                }
            )
            Text(
                text = task.description!!,
                modifier = Modifier.constrainAs(txtTaskDescription) {
                    top.linkTo(
                        txtTaskTitle.bottom,
                        margin = 10.dp
                    )
                    start.linkTo(
                        txtTaskTitle.start,
                        margin = 20.dp
                    )
                }
            )
            Text(
                text = getPriorityText(task.priority!!)["priorityText"] as String,
                modifier = Modifier.constrainAs(txtTaskPriority) {
                    top.linkTo(
                        txtTaskDescription.bottom,
                        margin = 10.dp
                    )
                    start.linkTo(parent.start, margin = 20.dp)
                    bottom.linkTo(parent.bottom, margin = 20.dp)
                }
            )
            Card(
                shape = ShapePriorityCard.large,
                colors = CardDefaults.cardColors(
                    containerColor = getPriorityText(task.priority!!)["color"]as Color
                ),
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(cardTaskPriorityColor) {
                        top.linkTo(txtTaskDescription.bottom)
                        start.linkTo(txtTaskPriority.end, margin = 10.dp)
                        bottom.linkTo(parent.bottom, margin = 10.dp)
                    }
            ) { }

            IconButton(
                onClick = {},
                modifier = Modifier.constrainAs(btnDelete) {
                    top.linkTo(txtTaskDescription.bottom, margin = 10.dp)
                    start.linkTo(txtTaskPriority.end, margin = 10.dp)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_delete),
                    contentDescription = ""
                )
            }

        }
    }
}


fun getPriorityText(level: Int): Map<String, Any> {
    return when (level) {
        1 -> mapOf("priorityText" to "Baixa", "color" to GreenRadioButtonSelected)
        2 -> mapOf("priorityText" to "Média", "color" to YellowRadioButtonSelected)
        3 -> mapOf("priorityText" to "Alta", "color" to RedRadioButtonSelected)
        else -> mapOf("priorityText" to "Sem prioridade", "color" to Color.Gray)
    }
}


@Preview(showBackground = true)
@Composable
fun TaskItemPreview() {
    val task = TaskModel("Comida para o cachorro", "Comprar comida da Lilly", 1)
    TaskItem(task)

}