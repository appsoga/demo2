package com.example.demo.web.controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;

import com.example.demo.data.Member;
import com.example.demo.repository.MemberRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sangmok.util.datatables.DataTablesRequest;
import sangmok.util.datatables.DataTablesResponse;
import sangmok.util.datatables.SpecificationFactory;

@RequestMapping(value = "app/member")
@Controller
public class MemberController {

    private static Logger logger = LoggerFactory.getLogger(MemberController.class);

    @Autowired
    private MemberRepository memberRepository;

    @RequestMapping(value = "list")
    public void member_list(Model model) {
        logger.debug("model: {}", model);

    }

    // @JsonView(JsonViewScope.List.class)
    @Transactional(readOnly = true)
    @RequestMapping(value = "datatable.json", method = RequestMethod.GET)
    public @ResponseBody DataTablesResponse<Member> list_json(
            @RequestParam(name = "agencyId", required = false, defaultValue = "0") Integer agencyId,
            @Valid @ModelAttribute DataTablesRequest request) {

        logger.info("list.json:: agencyId: {}", agencyId);

        // filter agency
        Specification<Member> specs = null;
        // if (agencyId != null && agencyId > 0) {
        // specs = Specifications.where(EmployeeSpecifications.equalAgency(agencyId));
        // } else {
        // Integer memberId = SecurityUtils.getMemberId();
        // Member member = memberService.findOne(memberId);
        // List<Agency> allAgencys = agencyService.findByMember(member);
        // specs = Specifications.where(EmployeeSpecifications.inAgency(allAgencys));
        // }

        specs = Specification.where(MemberSpecs.usernameNotNull());

        Specification<Member> spec1 = SpecificationFactory.createSpecification(request);
        specs = Specification.where(specs).and(spec1);
        Page<Member> page = memberRepository.findAll(specs, request.getPageable());
        // Page<Member> page = memberRepository.findAll(request.getPageable());

        logger.info("request is {}.", request);
        logger.info("page is {}.", page);

        return new DataTablesResponse<Member>(request, page);
    }

    public static class MemberSpecs {
        public static Specification<Member> usernameNotNull() {
            return new Specification<Member>() {
                private static final long serialVersionUID = 1L;

                @Override
                public Predicate toPredicate(Root<Member> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    return cb.isNotNull(root.get("username"));
                }
            };
        }
    }
}
