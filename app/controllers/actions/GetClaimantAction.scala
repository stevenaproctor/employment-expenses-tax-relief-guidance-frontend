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

package controllers.actions

import com.google.inject.{ImplementedBy, Inject}
import play.api.mvc.{ActionRefiner, Result}
import play.api.mvc.Results.Redirect
import controllers.routes
import models.requests.{ClaimantRequest, DataRequest}
import uk.gov.hmrc.play.HeaderCarrierConverter

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GetClaimantActionImpl @Inject() extends GetClaimantAction {

  override protected def refine[A](request: DataRequest[A]): Future[Either[Result, ClaimantRequest[A]]] = {
    implicit val hc = HeaderCarrierConverter.fromHeadersAndSession(request.headers, Some(request.session))

    request.userAnswers.claimant match {
      case None => Future.successful(Left(Redirect(routes.SessionExpiredController.onPageLoad())))
      case Some(claimant) => Future.successful(Right(ClaimantRequest(request.request, request.sessionId, request.userAnswers, claimant)))
    }
  }
}

@ImplementedBy(classOf[GetClaimantActionImpl])
trait GetClaimantAction extends ActionRefiner[DataRequest, ClaimantRequest]
