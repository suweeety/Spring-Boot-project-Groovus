!function (e) {
  "use strict";
  var t = function () {
    this.$body = e("body"),
      this.$modal = e("#event-modal"),
      this.$modal2 = e("#event-modal2"),
      this.$event = "#external-events div.external-event",
      this.$calendar = e("#calendar"),
      this.$saveCategoryBtn = e(".save-category"),
      this.$categoryForm = e("#add-category form"),
      this.$extEvents = e("#external-events"),
      this.$calendarObj = null
  };
  t.prototype.onDrop = function (t, n) {
    var a = t.data("eventObject"),
      o = t.attr("data-class"),
      i = e.extend({}, a);
    i.start = n, o && (i.className = [o]), this.$calendar.fullCalendar("renderEvent", i, !0), e("#drop-remove").is(":checked") && t.remove()
  }, t.prototype.onEventClick = function (t, n, a) {
    var o = this,
      i = e("<form></form>");
    i.append("<label>일정 제목</label>"), i
      .append("<div class='input-group'>" +
        "<input class='form-control' type=text value='" + t.title + "' style='border-radius: 10px; width: '/>" +
        "<span class='input-group-btn'>" +
        "<button type='submit' class='btn btn-success waves-effect waves-light'>" +
        "<i class='fa fa-check'></i>변경</button></span></div>"),
      o.$modal2.modal({
      backdrop: "static"
    }), o.$modal2.find(".cancel-event").show().end()
      .find(".save-event").hide().end()
      .find(".modal-body").empty().prepend(i).end()
      .find(".cancel-event").unbind("click").on("click", function () {
      o.$calendarObj.fullCalendar("removeEvents", function (e) {
        return e._id == t._id
      }), o.$modal2.modal("hide")
    }), o.$modal2.find("form").on("submit", function () {
      return t.title = i.find("input[type=text]").val(), o.$calendarObj.fullCalendar("updateEvent", t), o.$modal2.modal("hide"), !1
    })
  }
  t.prototype.onSelect = function (t, n, a) {
    var o = this;
    o.$modal.modal({
      backdrop: "static"
    });
    var i = e("<form></form>");
    i.append("<div class='row' style='height: 33rem;'></div>"), i.find(".row")
      .append("<div class='col-md-6' style='border-bottom: #0b0b0b'><div class='form-group' style='width: 16rem;'><label class='control-label'>일정 제목</label>" +
        "<input class='form-control' placeholder='Untitled' type='text' name='cal_title' style='width: 16rem; border-top: none;border-left: none;border-right: none;'/></div></div>")
      .append("<div class='col-md-6' style='max-width: 11rem; padding: 0; transform: translate(-87px, -1px);'><div class='form-group'><label class='control-label'>카테고리</label>" +
        "<select class='form-control' name='cal_cate' style='width: 9rem; border-radius: 10px;'></select></div></div>").find("select[name='cal_cate']")
      .append("<option value='bg-team'>팀 회의</option>")
      .append("<option value='bg-dept'>부서 회의</option>")
      .append("<option value='bg-company-event'>사내 행사</option>")
      .append("<option value='bg-personal-event'>개인 일정</option>")
      .append("<option value='bg-account-event'>거래처 일정</option>")
      .append("<option value='bg-business-trip'>출장</option>")
      .append("<option value='bg-etc'>기타</option></div></div>")
      .end()
      .append("<div class='col-md-6' style='width: 11rem; height: 5rem; transform: translate(460px, -96px); padding: 0;'><div class='form-group' style='margin: 0; height: 5rem;'><label class='control-label'>참여자</label>" +
        "<input class='form-control' id='cal_member_input' style='width: 18rem; border-radius: 10px;' /><span><button type='submit' style='width: 2rem; height: 36px; transform: translate(251px, -41px); border-radius: 10px; color: gray; background: none; border: none;'>➕</button></span>" +
        "</div><div name='cal_member' style='display: block;width: 11rem;height: 3rem; transform: translate(-95px, -77px); position: absolute;left: 35%;top: 4rem;padding: 10px;'>" +
        "<span class='badge badge-pill badge-success'>${create_user_id}<button type='button' name='removeTagBtn' style='border-radius: 10px; border: none;'>x</button></span>" +
        "<input type='hidden' name='cal_member' value='${user.uid}'></div>"+
        "<div class ='col' style='transform: translate(265px, -78px);'><div class='dropdown custom-dropdown'><div data-toggle='dropdown'><i class='fa fa-ellipsis-v'></i></div>"+
        "<div class='dropdown-menu dropdown-menu-right'><a class='dropdown-item' href='#'>Option 1</a><a class='dropdown-item' href='#'>Option 2</a>" +
        "<a class='dropdown-item' href='#'>Option 3</a></div></div></div></div>")

      .append("<div class='col-md-6' style='max-width: 46rem; height: 9rem; transform: translate(2px, -10px;)'><div class='form-group' style='transform: translate(0px, -84px);'><label class='control-label' style='transform: translate(0px, 11px);'>일정 내용</label>" +
        "<input class='form-control' placeholder='Contents...' type='textarea' name='cal_content' style='width: 46rem; height: 10rem; transform: translate(-1px, 10px); border-radius: 10px;'/></div></div>")
      .append("<div class='col-md-6' transform: translate(-2px, -22px);'><div class='form-group'><label class='control-label'>시작 날짜</label>" +
        "<input class='form-control' type='datetime-local' style='border-radius: 10px;' name='cal_startDate' /></div>"+
        "<div class='form-group' style='transform: translate(379px, -94px);'><label class='control-label'>종료 날짜</label>" +
        "<input class='form-control' type='datetime-local' name='cal_endDate' style='border-radius: 10px;' /></div></div>")
      .append("<div class='attach-button' style='transform: translate(-337px, 98px); height: 0rem;'>첨부파일</div><div class='file-upload-form' style='transform: translate(-388px, 137px); height: 2rem;'>" +
        "<input type='file' name='cal_attach' multiple class='file-input' style='width: 19rem;'/><button class='upload-button' style='border: none; background: none;'><i class='fa-solid fa-link'></i></button><div class='uploadResult'></div></div></div>")
      .append("<div class='col-md-6' style='border-bottom: #0b0b0b; transform: translate(377px, -89px);'><div class='form-group'><label class='control-label'>링크</label>" +
        "<input class='form-control' placeholder='링크 첨부(최대 1개)' type='url' name='cal_link' style='border-top: none;border-left: none;border-right: none;'/></div></div>")
      .append("<button type='button' class='btn btn-primary openReply' data-toggle='modal' data-target='.bd-example-modal-sm' style='border-radius: 12px;background-color: red;color: white;right: 10%;transform: translate(384px, -532px);height: 40px;'>💬</button>")

      , o.$modal.find(".delete-event").hide().end()
      .find(".save-event").show().end()
      .find(".modal-body").empty().prepend(i).end()

      ,
      $('.upload-button').click(function () {
        var formData = new FormData();

        var inputFile = $("input[type='file']");

        var files = inputFile[0].files;

        for (var i=0; i<files.length; i++) {
          console.log(files[i]);
          formData.append("cal_attach", files[i]);
        }

        $.ajax({
          url: '/uploadAjax',
          processData: false,
          contentType: false,
          data: formData,
          type: 'POST',
          dataType: 'json',
          success: function (result) {
            showUploadedImages(result);
          },
          error: function(jqXHR, textStatus, errorThrown) {
            console.log(textStatus);
          }
        })
      })
      ,
      function showUploadedImages(arr) {
        console.log(arr);

        const divArea = $(".uploadResult");

        var str = "";

        for (let i=0; i<arr.length; i++) {
          str += "<div>";
          str += "<img src='/display?fileName=" + arr[i].thumbnailURL + "'>";
          str += "<button class='removeBtn' data-name='" + arr[i].imageURL + "'>Remove</button>"
          str += "</div>";
        }
        divArea.append(str);
      }

    // 이벤트 핸들러 등록
    o.$modal.find(".save-event").unbind("click").on("click", function () {
      const eventData = {
        cal_title: i.find("input[name='cal_title']").val(),
        beginning: i.find("input[name='beginning']").val(),
        ending: i.find("input[name='ending']").val(),
        cal_cate: i.find("select[name='cal_cate'] option:checked").val(),
        cal_content: i.find("input[name='cal_content']").val(),
        cal_startDate: i.find("input[name='cal_startDate']").val(),
        cal_endDate: i.find("input[name='cal_endDate']").val(),
        cal_attach: i.find("input[name='cal_attach']").val(),
        cal_link: i.find("input[name='cal_link']").val()
      }

      $.ajax({
        url: '/calendar/register',
        type: 'POST',
        data: eventData,
        success: function (response) {
          alert("일정이 성공적으로 저장되었습니다.");
          console.log("일정이 성공적으로 저장되었습니다.");
        },
        error: function (xhr, status, error) {
          alert("일정 저장에 실패했습니다.");
          console.error("일정 저장에 실패했습니다:", error);
        }
      })
      i.submit();
    }), o.$modal.find("form").on("submit", function () {
      var e = i.find("input[name='cal_title']").val(),
        a = (i.find("select[name='cal_cate']").val(),
          i.find("input[name='cal_content']").val(),
          i.find("input[name='cal_startDate']").val(),
          i.find("input[name='cal_endDate']").val(),
          i.find("input[name='cal_attach']").val(),
          i.find("input[name='cal_link']").val());
      return null !== e && 0 != e.length ? (o.$calendarObj.fullCalendar("renderEvent", {
        title: e,
        start: t,
        end: n,
        allDay: !1,
        className: a
      }, !0), o.$modal.modal("hide")) : alert("제목을 입력하세요."), !1
    }), o.$calendarObj.fullCalendar("unselect")
  }
  , t.prototype.enableDrag = function () { //enableDrag: DOM 요소들을 선택해서 드래그하는 기능
    e(this.$event).each(function () {
      var t = {
        title: e.trim(e(this).text())
      };
      e(this).data("eventObject", t), e(this).draggable({
        zIndex: 999,
        revert: !0,
        revertDuration: 0
      })
    })
  }, t.prototype.init = function () {
    this.enableDrag();
    var t = new Date,
      n = (t.getDate(), t.getMonth(), t.getFullYear(), new Date(e.now())),
      a = [{
        title: "Hey!",
        start: new Date(e.now() + 158e6),
        className: "bg-team"
      }, {
        title: "See John Deo",
        start: n,
        end: n,
        className: "bg-team"
      }, {
        title: "Buy a Theme",
        start: new Date(e.now() + 338e6),
        className: "bg-team"
      }],
      o = this;
    o.$calendarObj = o.$calendar.fullCalendar({
      slotDuration: "00:15:00",
      minTime: "08:00:00",
      maxTime: "19:00:00",
      defaultView: "month",
      handleWindowResize: !0,
      height: e(window).height() - 200,
      header: {
        left: "prev today next",
        center: "title",
        right: "month,agendaWeek,agendaDay"
      },
      events: a,
      editable: !0,
      droppable: !0,
      eventLimit: !0,
      selectable: !0,
      drop: function (t) {
        o.onDrop(e(this), t)
      },
      select: function (e, t, n) {
        o.onSelect(e, t, n)
      },
      eventClick: function (e, t, n) {
        o.onEventClick(e, t, n)
      }
    }), this.$saveCategoryBtn.on("click", function () {
      var e = o.$categoryForm.find("input[name='category-name']").val(),
        t = o.$categoryForm.find("select[name='category-color']").val();
      null !== e && 0 != e.length && (o.$extEvents.append('<div class="external-event bg-' + t + '" data-class="bg-' + t + '" style="position: relative;"><i class="fa fa-move"></i>' + e + "</div>"), o.enableDrag())
    })
  }, e.CalendarApp = new t, e.CalendarApp.Constructor = t
}(window.jQuery),
  function (e) {
    "use strict";
    e.CalendarApp.init()
  }(window.jQuery);
