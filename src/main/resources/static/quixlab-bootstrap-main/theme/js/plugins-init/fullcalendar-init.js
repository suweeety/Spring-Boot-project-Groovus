!function (e) {
  "use strict";
  var t = function () {
    this.$body = e("body"),
      this.$modal = e("#event-modal"),
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
    i.append("<label>Change event name</label>"), i
      .append("<div class='input-group'>" +
        "<input class='form-control' type=text value='" + t.title + "' />" +
        "<span class='input-group-btn'>" +
        "<button type='submit' class='btn btn-success waves-effect waves-light'>" +
        "<i class='fa fa-check'></i> Save</button></span></div>"),
      o.$modal.modal({
      backdrop: "static"
    }), o.$modal.find(".delete-event").show().end()
      .find(".save-event").hide().end()
      .find(".modal-body").empty().prepend(i).end()
      .find(".delete-event").unbind("click").on("click", function () {
      o.$calendarObj.fullCalendar("removeEvents", function (e) {
        return e._id == t._id
      }), o.$modal.modal("hide")
    }), o.$modal.find("form").on("submit", function () {
      return t.title = i.find("input[type=text]").val(), o.$calendarObj.fullCalendar("updateEvent", t), o.$modal.modal("hide"), !1
    })
  }, t.prototype.onSelect = function (t, n, a) {
    var o = this;
    o.$modal.modal({
      backdrop: "static"
    });
    var i = e("<form action='/calendar/register' method='post'></form>");
    i.append("<div class='row'></div>"), i.find(".row")
      .append("<div class='col-md-6' style='border-bottom: #0b0b0b'><div class='form-group'><label class='control-label'>일정 제목</label>" +
        "<input class='form-control' placeholder='Untitled' type='text' name='cal_title' style='border-top: none;border-left: none;border-right: none;'/></div></div>")
      .append("<div class='col-md-6'><div class='form-group'><label class='control-label'>카테고리</label>" +
        "<select class='form-control' name='cal_category' style='width: 9rem; border-radius: 10px;'></select></div></div>").find("select[name='cal_category']")
      .append("<option value='bg-team'>팀 회의</option>")
      .append("<option value='bg-dept'>부서 회의</option>")
      .append("<option value='bg-company-event'>사내 행사</option>")
      .append("<option value='bg-personal-event'>개인 일정</option>")
      .append("<option value='bg-account-event'>거래처 일정</option>")
      .append("<option value='bg-business-trip'>출장</option>")
      .append("<option value='bg-etc'>기타</option></div></div>")
      .end()
      .append("<div class='col-md-6'><div class='form-group'><label class='control-label' style='transform: translate(0px, 11px);'>일정 내용</label>" +
        "<input class='form-control' placeholder='Contents...' type='text' name='cal_content' style='width: 35rem; height: 10rem; transform: translate(3px, 10px); border-radius: 10px;'/></div></div>")
      .append("<div class='col-md-6'><div class='form-group'><label class='control-label' style='transform: translate(-380px, 224px); width: 13rem;'>날짜</label>" +
        "<input class='form-control' type='datetime-local' name='cal_period' style='transform: translate(-382px, 219px); border-radius: 10px'/></div></div>")
      .append("<div class='col-md-6'><div class='form-group'><label class='control-label'>등록일: </label>" +
        "<input class='form-control' type='date' name='cal_title' /></div></div>")
      .append("<div class='plus-menu' style='transform: translate(15px, 104px);'><label class='plus-emo'>➕&nbsp;</label>" +
        "<button type='button' class='btn-dday' style='border-radius: 20px; background-color: ghostwhite; border-color: red; width: 4rem; height: 2rem;'>D-day</button>")

      // 버튼 클릭 이벤트 핸들러 함수
    $(".btn-dday").on("click", function() {
      // 첫 번째 모달 요소 생성
      var firstModal = e("#event-modal");

      // 두 번째 모달 요소 생성
      var secondModal = $("<div class='modal-content'>" +
        "<span class='close-button'>&times;</span>" +
        "<p>모달 내용</p>" +
        "</div>");

      // 첫 번째 모달의 모달 바디에 두 번째 모달 추가
      firstModal.find(".modal-body").append(secondModal);

      // 두 번째 모달의 닫기 버튼 클릭 이벤트 핸들러 함수
      secondModal.find(".close-button").on("click", function() {
        secondModal.remove(); // 두 번째 모달 제거
      });
    })

  , o.$modal.find(".delete-event").hide().end()
      .find(".save-event").show().end()
      .find(".modal-body").empty().prepend(i).end()
      .find(".save-event").unbind("click").on("click", function () {
        i.submit();
    }), o.$modal.find("form").on("submit", function () {
      var e = i.find("input[name='cal_title']").val(),
        a = (i.find("input[name='beginning']").val(), i.find("input[name='ending']").val(), i.find("select[name='cal_category'] option:checked").val());
      return null !== e && 0 != e.length ? (o.$calendarObj.fullCalendar("renderEvent", {
        title: e,
        start: t,
        end: n,
        allDay: !1,
        className: a
      }, !0), o.$modal.modal("hide")) : alert("제목을 입력하세요."), !1
    }), o.$calendarObj.fullCalendar("unselect")
  }, t.prototype.enableDrag = function () {
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
