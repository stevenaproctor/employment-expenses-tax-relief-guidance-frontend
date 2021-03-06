/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package forms

import forms.behaviours.OptionFieldBehaviours
import models.Claimant.You
import models.ClaimYears
import play.api.data.FormError

class TaxYearsFormProviderSpec extends OptionFieldBehaviours {

  val claimant = You
  val form = new TaxYearsFormProvider()(claimant)

  ".value" must {

    val fieldName = "value"
    val requiredKey = s"taxYears.$claimant.error.required"

    behave like optionsField[ClaimYears](
      form,
      fieldName,
      validValues  = ClaimYears.values.toSet,
      invalidError = FormError(fieldName, s"taxYears.$claimant.error.required")
    )

    behave like mandatoryField(
      form,
      fieldName,
      requiredError = FormError(fieldName, requiredKey)
    )
  }
}
