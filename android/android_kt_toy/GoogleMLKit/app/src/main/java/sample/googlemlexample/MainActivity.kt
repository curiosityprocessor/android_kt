package sample.googlemlexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.mlkit.nl.entityextraction.*

class MainActivity : AppCompatActivity() {
    private val tag = "EntityExtractionAPI"

    private val inputEditText by lazy {
        findViewById<EditText>(R.id.inputEditText)
    }

    private val result by lazy {
        findViewById<TextView>(R.id.result)
    }

    private val extractButton by lazy {
        findViewById<Button>(R.id.extractButton)
    }

    private lateinit var entityExtractor: EntityExtractor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        entityExtractor =
            EntityExtraction.getClient(
                EntityExtractorOptions.Builder(EntityExtractorOptions.KOREAN)
                    .build()
            )

        extractButton.setOnClickListener {
            val newInput = inputEditText.text.toString()
            if(newInput.isEmpty()) {
                Toast.makeText(this, "텍스트를 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            extractEntities(newInput)
        }
    }

    private fun extractEntities(input: String) {
        result.text = "추출중입니다."
        entityExtractor
            .downloadModelIfNeeded()
            .addOnSuccessListener { _ ->
                /* 추출 모델 다운로드 성공 */

                /* 추출 파라미터 생성 */
                val params =
                    EntityExtractionParams.Builder(input)
                        .build()

                /* 추출 API 호출 */
                entityExtractor.annotate(params)
                    .addOnSuccessListener {
                        /* 호출 성공 : entity list 반환  */
                        result.text = "추출완료\n"
                        for(entityAnnotation in it) {
                            val entities: List<Entity> = entityAnnotation.entities
                            Log.d(tag, "Range: ${entityAnnotation.start} - ${entityAnnotation.end}")
                            val annotatedText = entityAnnotation.annotatedText

                            /* 추출한 entity 별로 유형 분류, 처리 */
                            for(entity in entities) {
                                result.append("entity 유형: ${entity}\n")
                                result.append("entity 텍스트: $annotatedText\n\n")
                                when(entity.type) {
                                    Entity.TYPE_DATE_TIME -> {Log.d(tag, "날짜/시간 관련 추가 기능 제공")}
                                    Entity.TYPE_ADDRESS -> {Log.d(tag, "주소 관련 추가 기능 제공")}
                                    Entity.TYPE_MONEY -> {Log.d(tag, "금액/화폐 관련 추가 기능 제공")}
                                    else -> Log.d(tag, "기타")
                                }
                            }
                        }
                    }
                    .addOnFailureListener {
                        /* 호출 실패 */
                        Log.e(tag, "EntityExtractor.annotate() failed: ${it.localizedMessage}")
                    }
            }
            .addOnFailureListener {
                /* 추출 모델 다운로드 실패 */
                Log.e(tag, "EntityExtractor.downloadModelInNeeded() failed: ${it.localizedMessage}")
            }
    }
}