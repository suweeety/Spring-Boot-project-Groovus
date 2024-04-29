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
    }), o.$modal.find(".cancel-event").show().end()
      .find(".save-event").hide().end()
      .find(".modal-body").empty().prepend(i).end()
      .find(".cancel-event").unbind("click").on("click", function () {
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
    var i = e("<form></form>");
    i.append("<div class='row'></div>"), i.find(".row")
      .append("<div class='col-md-6' style='border-bottom: #0b0b0b'><div class='form-group'><label class='control-label'>일정 제목</label>" +
        "<input class='form-control' placeholder='Untitled' type='text' name='cal_title' style='border-top: none;border-left: none;border-right: none;'/></div></div>")
      .append("<div class='col-md-6'><div class='form-group'><label class='control-label'>카테고리</label>" +
        "<select class='form-control' name='cal_category' style='width: 9rem; border-radius: 10px;'></select></div></div>").find("select[name='cal_category']")
      .append("<option name='cal_category' value='bg-team'>팀 회의</option>")
      .append("<option name='cal_category' value='bg-dept'>부서 회의</option>")
      .append("<option name='cal_category' value='bg-company-event'>사내 행사</option>")
      .append("<option name='cal_category' value='bg-personal-event'>개인 일정</option>")
      .append("<option name='cal_category' value='bg-account-event'>거래처 일정</option>")
      .append("<option name='cal_category' value='bg-business-trip'>출장</option>")
      .append("<option name='cal_category' value='bg-etc'>기타</option></div></div>")
      .end()
      .append("<div class='col-md-6' style='width: 9rem; transform: translate(567px, -94px);'><div class='form-group'><label class='control-label'>참여자</label>" +
        "<input class='form-control' id='cal_member_input' name='cal_member' style='width: 9rem; border-radius: 10px;' /></div></div><div id='selected_members'></div>")

      .append("<div class='col-md-6' style='max-width: 46rem; height: 10rem; transform: translate(-1px, -75px;)'><div class='form-group'><label class='control-label' style='transform: translate(0px, 11px);'>일정 내용</label>" +
        "<input class='form-control' placeholder='Contents...' type='text' name='cal_content' style='width: 46rem; height: 10rem; transform: translate(-1px, 10px); border-radius: 10px;'/></div></div>")
      .append("<div class='col-md-6' style='text-align: center; transform: translate(-10px, -26px);'><div class='form-group'><label class='control-label'>이벤트 시간 설정</label>" +
        "<div class='btn-group' style='transform: translate(-106px, 41px);' data-toggle='buttons'>" +
        "<label class='btn btn-primary active' style='border-radius: 20px;'>" +
        "<input type='radio' name='cal_allDay' id='option1' autocomplete='off' checked> 하루 종일" +
        "</label>" +
        "<label class='btn btn-primary' style='border-radius: 20px;'>" +
        "<input type='radio' name='cal_allDay' id='option2' autocomplete='off'> 시간 설정" +
        "</label></div>")
      .append("<div class='col-md-6 start-time' style='display:none'><div class='form-group'><label class='control-label'>시작 날짜</label>" +
        "<input class='form-control' type='datetime-local' name='cal_startDate' /></div></div>")
      .append("<div class='col-md-6 end-time' style='display:none'><div class='form-group'><label class='control-label'>종료 날짜</label>" +
        "<input class='form-control' type='datetime-local' name='cal_endDate' /></div></div></div>")

      .append("<div class='col-md-6' style='transform: translate(-380px, 75px);'><div class='form-group'><label class='control-label'>첨부파일</label>" +
        "<div class='dropdown' style='float: right;'><button class='btn btn-secondary dropdown-toggle' type='button' id='dropdownMenuButton' data-toggle='dropdown'" +
        "aria-haspopup='true' aria-expanded='false'>v</button>" +
        "<div class='dropdown-menu' aria-labelledby='dropdownMenuButton'>첨부파일 표시</div></div></div>")
    $(function() {
      var availableMembers = [
        "멤버1",
        "멤버2",
        "멤버3",
        // 추가 멤버들
      ];
    })
    // 시간 설정 라디오 버튼 클릭 이벤트 핸들러
    // 라디오 버튼의 변경 이벤트 핸들러 등록
    , $("input[name='cal_allDay']").change(function() {
      // 시간 설정 라디오 버튼 클릭 이벤트 핸들러
      // 선택된 라디오 버튼의 값을 확인
      if ($(this).is('checked')) {
        $(".start-time, .end-time").show();
      } else {
        $(".start-time, .end-time").hide();
      }
    })

  , o.$modal.find(".delete-event").hide().end()
      .find(".save-event").show().end()
      .find(".modal-body").empty().prepend(i).end()

    // 이벤트 핸들러 등록
    o.$modal.find(".save-event").unbind("click").on("click", function () {
      const eventData = {
        cal_title: i.find("input[name='cal_title']").val(),
        beginning: i.find("input[name='beginning']").val(),
        ending: i.find("input[name='ending']").val(),
        cal_category: i.find("select[name='cal_category'] option:checked").val(),
        cal_content: i.find("input[name='cal_content']").val(),
        cal_startDate: i.find("input[name='cal_startDate']").val(),
        cal_endDate: i.find("input[name='cal_endDate']").val()
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

    }), o.$modal.find("form").on("submit", function () {
      var e = i.find("input[name='cal_title']").val(),
        a = (i.find("input[name='beginning']").val(),
          i.find("input[name='ending']").val(),
          i.find("select[name='cal_category']").val(),
          i.find("input[name='cal_content']").val(),
          i.find("input[name='cal_startDate']").val(),
        i.find("input[name='cal_endDate']").val());
      return null !== e && 0 != e.length ? (o.$calendarObj.fullCalendar("renderEvent", {
        cal_title: e,
        start: t,
        end: n,
        allDay: !1,
        className: a,
        cal_content: i,
        cal_category: i,
        cal_startDate: i,
        cal_endDate: i
      }, !0), o.$modal.modal("hide")) : alert("제목을 입력하세요."), !1
    }), o.$calendarObj.fullCalendar("unselect")
  }, t.prototype.enableDrag = function () { //enableDrag: DOM 요소들을 선택해서 드래그하는 기능
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
